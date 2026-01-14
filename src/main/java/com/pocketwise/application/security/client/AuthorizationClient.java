package com.pocketwise.application.security.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pocketwise.application.common.configuration.FeignClientConfiguration;
import com.pocketwise.application.security.dto.EnableBankingAuthorizationRequestDTO;
import com.pocketwise.application.security.dto.EnableBankingAuthorizationResponseDTO;

@FeignClient(
        name = "authorizationClient",
        url = "${integration.api.base-url}",
        configuration = FeignClientConfiguration.class)
public interface AuthorizationClient {

    /**
     * Performs the authorization process for the given request.
     *
     * @param request The {@link EnableBankingAuthorizationRequestDTO} containing the necessary details for authorization.
     * @return The {@link EnableBankingAuthorizationResponseDTO} containing the authorization response, including the authorization URL and ID.
     */
    @PostMapping("/auth")
    EnableBankingAuthorizationResponseDTO authorize(@RequestBody EnableBankingAuthorizationRequestDTO request);
}
