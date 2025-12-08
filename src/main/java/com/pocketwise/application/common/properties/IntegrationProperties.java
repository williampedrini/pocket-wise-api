package com.pocketwise.application.common.properties;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "integration.api")
public class IntegrationProperties {

    @NotBlank(message = "API base URL must not be blank")
    private String baseUrl;

    @NotBlank(message = "Application ID must not be blank")
    private String applicationId;

    @NotBlank(message = "Private key path must not be blank")
    private String privateKeyPath;

    @NotNull
    @Min(value = 60, message = "JWT expiration must be at least 60 seconds")
    private Integer jwtExpirationSeconds = 3600;

    @NotNull
    @Min(value = 1000, message = "Connect timeout must be at least 1000ms")
    private Integer connectTimeout = 10000;

    @NotNull
    @Min(value = 1000, message = "Read timeout must be at least 1000ms")
    private Integer readTimeout = 30000;

    @NotNull
    private Retry retry = new Retry();

    @Data
    public static class Retry {

        @Min(value = 1, message = "Max attempts must be at least 1")
        private Integer maxAttempts = 3;

        @Min(value = 100, message = "Initial interval must be at least 100ms")
        private Long initialInterval = 1000L;

        @Min(value = 1, message = "Multiplier must be at least 1")
        private Double multiplier = 2.0;
    }
}
