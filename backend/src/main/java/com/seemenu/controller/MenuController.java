package com.seemenu.controller;

import com.seemenu.dto.MenuAnalysisResponse;
import com.seemenu.service.MenuService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/menu")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MenuController {

    private final MenuService menuService;

    @PostMapping("/upload")
    public ResponseEntity<MenuAnalysisResponse> uploadMenu(@RequestParam("file") MultipartFile file) {
        try {
            MenuAnalysisResponse response = menuService.analyzeMenu(file);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
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
