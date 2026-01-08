package com.pocketwise.application.common.configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.hazelcast.config.Config;
import com.hazelcast.config.MapConfig;
import com.pocketwise.application.common.properties.CacheProperties;
import com.pocketwise.application.common.properties.CacheProperties.TierConfig;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableCaching
@RequiredArgsConstructor
@EnableConfigurationProperties(CacheProperties.class)
class HazelcastConfiguration {

    private final CacheProperties properties;

    @Bean
    Config config() {
        final TierConfig accounts = properties.accounts();
        final TierConfig balances = properties.balances();
        final TierConfig free = properties.transactions().free();
        final TierConfig premium = properties.transactions().premium();

        return new Config()
                .setInstanceName(properties.instanceName())
                .addMapConfig(createMapConfig(accounts.name(), accounts.ttlHours()))
                .addMapConfig(createMapConfig(balances.name(), balances.ttlHours()))
                .addMapConfig(createMapConfig(free.name(), free.ttlHours()))
                .addMapConfig(createMapConfig(premium.name(), premium.ttlHours()));
    }

    private MapConfig createMapConfig(String name, int ttlHours) {
        return new MapConfig().setName(name).setTimeToLiveSeconds((int) TimeUnit.HOURS.toSeconds(ttlHours));
    }
}
