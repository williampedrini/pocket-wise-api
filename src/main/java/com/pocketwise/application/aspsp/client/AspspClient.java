package com.pocketwise.application.aspsp.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import com.pocketwise.application.aspsp.dto.EnableBankingAspspListDTO;
import com.pocketwise.application.common.configuration.FeignClientConfiguration;

@FeignClient(name = "aspspClient", url = "${integration.api.base-url}", configuration = FeignClientConfiguration.class)
public interface AspspClient {

    /**
     * Retrieves the list of available ASPSPs (Account Servicing Payment Service Providers).
     *
     * @return an {@link EnableBankingAspspListDTO} containing all available ASPSPs with their metadata.
     */
    @GetMapping("/aspsps")
    EnableBankingAspspListDTO findAll();
}
