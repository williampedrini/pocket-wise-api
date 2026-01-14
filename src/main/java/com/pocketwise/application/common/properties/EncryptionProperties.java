package com.pocketwise.application.common.properties;

import jakarta.validation.constraints.NotBlank;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;

import lombok.Data;

@Data
@Validated
@Configuration
@ConfigurationProperties(prefix = "encryption")
public class EncryptionProperties {

    @NotBlank(message = "Encryption secret key must not be blank")
    private String secretKey;
}
