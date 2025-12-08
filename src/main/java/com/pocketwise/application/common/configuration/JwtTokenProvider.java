package com.pocketwise.application.common.configuration;

import static io.jsonwebtoken.SignatureAlgorithm.RS256;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Objects.requireNonNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.security.PrivateKey;
import java.util.Date;

import jakarta.annotation.Nonnull;

import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.openssl.PEMParser;
import org.bouncycastle.openssl.jcajce.JcaPEMKeyConverter;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import com.pocketwise.application.common.properties.IntegrationProperties;

import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

    private final IntegrationProperties properties;
    private final ResourceLoader resourceLoader;
    private final PrivateKey privateKey;

    public JwtTokenProvider(
            @Nonnull final IntegrationProperties properties, @Nonnull final ResourceLoader resourceLoader) {
        try {
            this.properties = requireNonNull(properties, "Properties are mandatory.");
            this.resourceLoader = requireNonNull(resourceLoader, "Resource loader is mandatory.");
            this.privateKey = loadPrivateKey();
            log.info("Successfully loaded private key for Enable Banking authentication");
        } catch (final Exception exception) {
            log.error("Failed to load private key from: {}", properties.getPrivateKeyPath(), exception);
            throw new IllegalStateException("Failed to initialize JWT Token Provider", exception);
        }
    }

    public String generateToken() {
        final long nowMillis = System.currentTimeMillis();
        final Date now = new Date(nowMillis);
        final long expirationMillis = nowMillis + (properties.getJwtExpirationSeconds());
        final Date expiration = new Date(expirationMillis);
        final String token = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setHeaderParam("alg", "RS256")
                .setHeaderParam("kid", properties.getApplicationId())
                .setIssuer("enablebanking.com")
                .setAudience("api.enablebanking.com")
                .setIssuedAt(now)
                .setExpiration(expiration)
                .signWith(privateKey, RS256)
                .compact();
        log.debug("Generated JWT token for application: {}", properties.getApplicationId());
        return token;
    }

    private PrivateKey loadPrivateKey() throws IOException {
        final Resource resource = resourceLoader.getResource(properties.getPrivateKeyPath());
        if (resource.exists()) {
            try (final InputStreamReader reader = new InputStreamReader(resource.getInputStream(), UTF_8);
                    final PEMParser pemParser = new PEMParser(reader)) {
                if (pemParser.readObject() instanceof PrivateKeyInfo key) {
                    JcaPEMKeyConverter converter = new JcaPEMKeyConverter();
                    return converter.getPrivateKey(key);
                }
                throw new IllegalStateException("Invalid private key format. Expected PKCS#8 format.");
            }
        }
        throw new IllegalStateException("Private key file not found: " + properties.getPrivateKeyPath());
    }
}
