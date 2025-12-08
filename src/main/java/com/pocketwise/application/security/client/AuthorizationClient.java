package com.pocketwise.application.security.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.pocketwise.application.common.configuration.FeignClientConfiguration;
import com.pocketwise.application.security.dto.AuthorizationRequestDTO;
import com.pocketwise.application.security.dto.AuthorizationResponseDTO;

@FeignClient(
        name = "authorizationClient",
        url = "${integration.api.base-url}",
        configuration = FeignClientConfiguration.class)
public interface AuthorizationClient {

    /**
     * Performs the authorization process for the given request.
     *
     * @param request The {@link AuthorizationRequestDTO} containing the necessary details for authorization.
     * @return The {@link AuthorizationResponseDTO} containing the authorization response, including the authorization URL and ID.
     */
    @PostMapping("/auth")
    AuthorizationResponseDTO authorize(@RequestBody AuthorizationRequestDTO request);
}
