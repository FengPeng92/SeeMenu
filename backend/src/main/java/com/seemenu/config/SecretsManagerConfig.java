package com.seemenu.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.secretsmanager.SecretsManagerClient;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueRequest;
import software.amazon.awssdk.services.secretsmanager.model.GetSecretValueResponse;

@Configuration
@Slf4j
public class SecretsManagerConfig {

    @Value("${openai.secret.arn:#{null}}")
    private String openaiSecretArn;

    @Bean
    public String openaiApiKey() {
        // First, try to get from environment variable (for local development)
        String apiKey = System.getenv("OPENAI_API_KEY");

        if (apiKey != null && !apiKey.isEmpty()) {
            log.info("Using OpenAI API key from environment variable");
            return apiKey;
        }

        // If not in environment, try to get from Secrets Manager (for Lambda)
        if (openaiSecretArn != null && !openaiSecretArn.isEmpty()) {
            log.info("Retrieving OpenAI API key from Secrets Manager: {}", openaiSecretArn);
            try {
                SecretsManagerClient client = SecretsManagerClient.builder()
                        .region(Region.US_EAST_1)
                        .build();

                GetSecretValueRequest request = GetSecretValueRequest.builder()
                        .secretId(openaiSecretArn)
                        .build();

                GetSecretValueResponse response = client.getSecretValue(request);
                String secretValue = response.secretString();

                log.info("Successfully retrieved OpenAI API key from Secrets Manager");
                log.debug("API Key starts with: {}", secretValue != null ? secretValue.substring(0, Math.min(10, secretValue.length())) + "..." : "NULL");

                client.close();
                return secretValue;
            } catch (Exception e) {
                log.error("Error retrieving secret from Secrets Manager: {}", e.getMessage(), e);
                throw new RuntimeException("Failed to retrieve OpenAI API key from Secrets Manager", e);
            }
        }

        log.error("OpenAI API key not found in environment variable or Secrets Manager");
        throw new RuntimeException("OpenAI API key not configured");
    }
}
