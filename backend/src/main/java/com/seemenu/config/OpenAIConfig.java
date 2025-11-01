package com.seemenu.config;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class OpenAIConfig {

    private final String openaiApiKey;

    @Bean
    public WebClient openAiWebClient() {
        log.info("Configuring OpenAI WebClient with API key starting with: {}",
                openaiApiKey != null ? openaiApiKey.substring(0, Math.min(10, openaiApiKey.length())) + "..." : "NULL");

        return WebClient.builder()
                .baseUrl("https://api.openai.com/v1")
                .defaultHeader(HttpHeaders.AUTHORIZATION, "Bearer " + openaiApiKey)
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }
}
