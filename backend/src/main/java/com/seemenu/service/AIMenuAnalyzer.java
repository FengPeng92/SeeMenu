package com.seemenu.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.seemenu.model.DishInfo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AIMenuAnalyzer {

    private final WebClient openAiWebClient;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${openai.model}")
    private String model;

    public List<DishInfo> analyzeMenuFromBase64(String base64Image) {
        log.info("Analyzing menu with AI model: {}", model);

        String prompt = buildAnalysisPrompt();

        try {
            // Build request body for OpenAI Vision API
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", model);
            requestBody.put("max_tokens", 2000);
            requestBody.put("temperature", 0.3);

            // Force JSON mode for structured output (ensures valid JSON)
            Map<String, String> responseFormat = new HashMap<>();
            responseFormat.put("type", "json_object");
            requestBody.put("response_format", responseFormat);

            // Build messages array
            List<Map<String, Object>> messages = new ArrayList<>();

            Map<String, Object> userMessage = new HashMap<>();
            userMessage.put("role", "user");

            // Content is an array with text and image
            List<Map<String, Object>> content = new ArrayList<>();

            // Text content
            Map<String, Object> textContent = new HashMap<>();
            textContent.put("type", "text");
            textContent.put("text", prompt);
            content.add(textContent);

            // Image content
            Map<String, Object> imageContent = new HashMap<>();
            imageContent.put("type", "image_url");
            Map<String, String> imageUrl = new HashMap<>();
            imageUrl.put("url", "data:image/jpeg;base64," + base64Image);
            imageContent.put("image_url", imageUrl);
            content.add(imageContent);

            userMessage.put("content", content);
            messages.add(userMessage);

            requestBody.put("messages", messages);

            // Call OpenAI API
            log.info("Calling OpenAI API...");
            String responseJson = openAiWebClient.post()
                    .uri("/chat/completions")
                    .bodyValue(requestBody)
                    .retrieve()
                    .onStatus(status -> status.is4xxClientError() || status.is5xxServerError(),
                            response -> response.bodyToMono(String.class)
                                    .map(errorBody -> {
                                        log.error("OpenAI API error response: {}", errorBody);
                                        return new RuntimeException("OpenAI API error: " + errorBody);
                                    }))
                    .bodyToMono(String.class)
                    .block();

            log.info("Received raw response from OpenAI");
            log.debug("Full OpenAI response: {}", responseJson);

            // Parse response
            JsonNode responseNode = objectMapper.readTree(responseJson);

            // Check for errors
            if (responseNode.has("error")) {
                String errorMsg = responseNode.path("error").path("message").asText();
                log.error("OpenAI API error: {}", errorMsg);
                throw new RuntimeException("OpenAI API error: " + errorMsg);
            }

            String aiResponse = responseNode
                    .path("choices")
                    .get(0)
                    .path("message")
                    .path("content")
                    .asText();

            log.info("AI response content: {}", aiResponse);

            return parseAIResponse(aiResponse);

        } catch (Exception e) {
            log.error("Error analyzing menu with AI: {}", e.getMessage(), e);
            return Collections.emptyList();
        }
    }

    private String buildAnalysisPrompt() {
        return """
                Analyze this restaurant menu image and extract detailed information about each dish.

                For each dish, provide:
                1. Name of the dish
                2. Description (if available)
                3. Price
                4. List of ingredients (if mentioned or can be inferred)
                5. Common allergens (dairy, gluten, nuts, shellfish, etc.)
                6. Dietary information (vegetarian, vegan, gluten-free, etc.)

                Return your response as a JSON object with a "dishes" array containing this structure:
                {
                  "dishes": [
                    {
                      "name": "Dish Name",
                      "description": "Brief description",
                      "price": "$XX.XX",
                      "ingredients": ["ingredient1", "ingredient2"],
                      "allergens": ["allergen1", "allergen2"],
                      "dietaryInfo": ["vegetarian", "gluten-free"]
                    }
                  ]
                }

                IMPORTANT: Return valid JSON only, no markdown code blocks or additional text.
                """;
    }

    private List<DishInfo> parseAIResponse(String response) {
        List<DishInfo> dishes = new ArrayList<>();

        try {
            log.info("Parsing AI response...");
            // Try to extract JSON from response
            String jsonString = extractJSON(response);
            log.info("Extracted JSON: {}", jsonString);

            JsonNode rootNode = objectMapper.readTree(jsonString);

            // Handle both old format (array) and new format (object with "dishes" array)
            JsonNode dishesNode;
            if (rootNode.isArray()) {
                dishesNode = rootNode;
            } else if (rootNode.has("dishes") && rootNode.get("dishes").isArray()) {
                dishesNode = rootNode.get("dishes");
            } else {
                log.warn("Response is neither an array nor an object with 'dishes' array: {}", rootNode);
                return dishes;
            }

            log.info("Found {} dishes in response", dishesNode.size());
            for (JsonNode dishNode : dishesNode) {
                DishInfo dish = parseDishNode(dishNode);
                dishes.add(dish);
                log.debug("Parsed dish: {}", dish.getName());
            }

        } catch (JsonProcessingException e) {
            log.error("Error parsing AI response: {}", e.getMessage());
            log.error("Response was: {}", response);
        }

        log.info("Returning {} dishes", dishes.size());
        return dishes;
    }

    private String extractJSON(String response) {
        // Remove markdown code blocks if present
        String cleaned = response.trim();
        if (cleaned.startsWith("```json")) {
            cleaned = cleaned.substring(7);
        } else if (cleaned.startsWith("```")) {
            cleaned = cleaned.substring(3);
        }
        if (cleaned.endsWith("```")) {
            cleaned = cleaned.substring(0, cleaned.length() - 3);
        }
        return cleaned.trim();
    }

    private DishInfo parseDishNode(JsonNode node) {
        DishInfo dish = new DishInfo();

        if (node.has("name")) {
            dish.setName(node.get("name").asText());
        }
        if (node.has("description")) {
            dish.setDescription(node.get("description").asText());
        }
        if (node.has("price")) {
            dish.setPrice(node.get("price").asText());
        }
        if (node.has("ingredients") && node.get("ingredients").isArray()) {
            List<String> ingredients = new ArrayList<>();
            node.get("ingredients").forEach(item -> ingredients.add(item.asText()));
            dish.setIngredients(ingredients);
        }
        if (node.has("allergens") && node.get("allergens").isArray()) {
            List<String> allergens = new ArrayList<>();
            node.get("allergens").forEach(item -> allergens.add(item.asText()));
            dish.setAllergens(allergens);
        }
        if (node.has("dietaryInfo") && node.get("dietaryInfo").isArray()) {
            List<String> dietaryInfo = new ArrayList<>();
            node.get("dietaryInfo").forEach(item -> dietaryInfo.add(item.asText()));
            dish.setDietaryInfo(dietaryInfo);
        }

        return dish;
    }
}
