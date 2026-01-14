package com.pocketwise.application.aspsp.service;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toCollection;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jakarta.annotation.Nonnull;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.pocketwise.application.aspsp.client.AspspClient;
import com.pocketwise.application.aspsp.dto.EnableBankingAspspDTO;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AspspService {

    private final AspspClient client;

    /**
     * Retrieves all ASPSPs grouped by country.
     *
     * @return a map where the key is the ISO 3166-1 alpha-2 country code and the value
     *         is a list of {@link EnableBankingAspspDTO} objects for that country;
     *         an empty map is returned if no ASPSPs are found.
     */
    @Nonnull
    @Cacheable(cacheNames = "#{@cacheProperties.aspsps().name()}", key = "'all'")
    public Map<String, List<EnableBankingAspspDTO>> findAll() {
        log.debug("Fetching all ASPSPs grouped by country");
        return client.findAll().aspsps().stream()
                .collect(groupingBy(EnableBankingAspspDTO::country, TreeMap::new, toCollection(ArrayList::new)));
    }
}
