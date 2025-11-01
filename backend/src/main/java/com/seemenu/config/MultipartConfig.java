package com.seemenu.config;

import jakarta.servlet.MultipartConfigElement;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.unit.DataSize;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;

/**
 * Multipart configuration for Lambda environment
 * Forces Spring to enable multipart parsing in aws-serverless-java-container
 */
@Configuration
public class MultipartConfig {

    /**
     * Configure multipart file upload settings
     * Important: Lambda requires temp files to be under /tmp
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setMaxFileSize(DataSize.ofMegabytes(10));
        factory.setMaxRequestSize(DataSize.ofMegabytes(10));
        // Lambda requires temp files under /tmp (writable location)
        factory.setLocation("/tmp");
        return factory.createMultipartConfig();
    }

    /**
     * Ensure Spring uses Servlet-based multipart resolver
     * This is critical for Lambda - auto-configuration may not activate
     */
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}
