package com.pocketwise.application.common.configuration;

import static feign.Logger.Level.FULL;
import static java.util.concurrent.TimeUnit.MILLISECONDS;
import static java.util.concurrent.TimeUnit.SECONDS;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.pocketwise.application.common.properties.IntegrationProperties;
import com.pocketwise.application.common.properties.IntegrationProperties.Retry;

import feign.Logger.Level;
import feign.Request.Options;
import feign.Retryer;
import feign.Retryer.Default;
import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class FeignClientConfiguration {

    private final IntegrationProperties properties;

    @Bean
    Options options() {
        return new Options(
                properties.getConnectTimeout(), MILLISECONDS, properties.getReadTimeout(), MILLISECONDS, true);
    }

    @Bean
    Retryer retryer() {
        final Retry retryConfig = properties.getRetry();
        return new Default(
                retryConfig.getInitialInterval(),
                SECONDS.toMillis(properties.getJwtExpirationSeconds()),
                retryConfig.getMaxAttempts());
    }

    @Bean
    Level logger() {
        return FULL;
    }
}
