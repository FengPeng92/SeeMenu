package com.seemenu.controller;

import com.seemenu.dto.MenuAnalysisResponse;
import com.seemenu.service.MenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MenuController {

    private final MenuService menuService;

    @PostMapping(value = "/upload", consumes = {"image/*", "application/octet-stream"})
    public ResponseEntity<MenuAnalysisResponse> uploadMenu(
            @RequestBody byte[] bytes,
            @RequestHeader(value = "Content-Type", required = false) String contentType,
            @RequestHeader(value = "X-Filename", required = false) String filename) {
        try {
            log.info("Binary upload received: size={}, contentType={}, filename={}",
                    bytes.length, contentType, filename);

            // Create a simple MultipartFile wrapper for the service
            MultipartFile file = new MultipartFile() {
                @Override
                public String getName() { return "file"; }

                @Override
                public String getOriginalFilename() {
                    return filename != null ? filename : "upload";
                }

                @Override
                public String getContentType() { return contentType; }

                @Override
                public boolean isEmpty() { return bytes.length == 0; }

                @Override
                public long getSize() { return bytes.length; }

                @Override
                public byte[] getBytes() { return bytes; }

                @Override
                public java.io.InputStream getInputStream() {
                    return new java.io.ByteArrayInputStream(bytes);
                }

                @Override
                public void transferTo(java.io.File dest) throws java.io.IOException {
                    java.nio.file.Files.write(dest.toPath(), bytes);
                }
            };

            MenuAnalysisResponse response = menuService.analyzeMenu(file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            log.error("Error processing menu upload", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(MenuAnalysisResponse.builder()
                            .success(false)
                            .message("Error processing menu: " + e.getMessage())
                            .build());
        }
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("SeeMenu API is running");
    }
}
