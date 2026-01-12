package com.pocketwise.application.aspsp.service;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import jakarta.annotation.Nonnull;

import org.springframework.stereotype.Service;

import com.pocketwise.application.aspsp.client.AspspClient;
import com.pocketwise.application.aspsp.dto.AspspDTO;

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
     *         is a list of {@link AspspDTO} objects for that country;
     *         an empty map is returned if no ASPSPs are found.
     */
    @Nonnull
    public Map<String, List<AspspDTO>> findAll() {
        log.debug("Fetching all ASPSPs grouped by country");
        return client.findAll().aspsps().stream().collect(groupingBy(AspspDTO::country, TreeMap::new, toList()));
    }
}
