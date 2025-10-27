package com.seemenu.service;

import com.seemenu.dto.MenuAnalysisResponse;
import com.seemenu.model.DishInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Base64;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class MenuService {

    private final AIMenuAnalyzer aiMenuAnalyzer;

    public MenuAnalysisResponse analyzeMenu(MultipartFile file) {
        log.info("Processing menu image: {}", file.getOriginalFilename());

        try {
            // Convert image to base64
            byte[] imageBytes = file.getBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            // Analyze with AI
            List<DishInfo> dishes = aiMenuAnalyzer.analyzeMenuFromBase64(base64Image);

            if (dishes.isEmpty()) {
                return MenuAnalysisResponse.builder()
                        .success(false)
                        .message("Could not extract dish information from the menu. Please ensure the image is clear and contains a menu.")
                        .build();
            }

            return MenuAnalysisResponse.builder()
                    .success(true)
                    .message("Menu analyzed successfully! Found " + dishes.size() + " dish(es).")
                    .dishes(dishes)
                    .build();

        } catch (Exception e) {
            log.error("Error processing menu image", e);
            return MenuAnalysisResponse.builder()
                    .success(false)
                    .message("Error processing menu: " + e.getMessage())
                    .build();
        }
    }
}
