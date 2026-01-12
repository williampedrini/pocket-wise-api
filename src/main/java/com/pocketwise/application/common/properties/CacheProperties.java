package com.pocketwise.application.common.properties;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

@Validated
@ConfigurationProperties(prefix = "cache")
public record CacheProperties(
        @NotBlank(message = "Cache instance name must not be blank") String instanceName,
        @NotNull(message = "Accounts cache configuration is required") @Valid TierConfig accounts,
        @NotNull(message = "Balances cache configuration is required") @Valid TierConfig balances,
        @NotNull(message = "Transaction cache configuration is required") @Valid TransactionCache transactions,
        @NotNull(message = "Countries cache configuration is required") @Valid PersistentCacheConfig countries) {

    public record TransactionCache(
            @NotNull(message = "Free tier configuration is required") @Valid TierConfig free,
            @NotNull(message = "Premium tier configuration is required") @Valid TierConfig premium) {}

    public record TierConfig(
            @NotBlank(message = "Cache name must not be blank") String name,
            @NotNull(message = "TTL hours must not be null") @Min(value = 1, message = "TTL must be at least 1 hour")
                    Integer ttlHours) {}

    public record PersistentCacheConfig(@NotBlank(message = "Cache name must not be blank") String name) {}
}
