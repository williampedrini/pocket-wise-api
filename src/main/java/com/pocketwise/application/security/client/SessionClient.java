package com.pocketwise.application.security.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.pocketwise.application.common.configuration.FeignClientConfiguration;
import com.pocketwise.application.security.dto.EnableBankingAuthenticationRequestDTO;
import com.pocketwise.application.security.dto.EnableBankingCreateSessionResponseDTO;

@FeignClient(
        name = "sessionClient",
        url = "${integration.api.base-url}/sessions",
        configuration = FeignClientConfiguration.class)
public interface SessionClient {

    /**
     * Creates a new session based on the provided authentication request.
     *
     * @param request The {@link EnableBankingAuthenticationRequestDTO} containing the required authentication details.
     * @return A {@link EnableBankingCreateSessionResponseDTO} containing the details of the newly created session.
     */
    @PostMapping
    EnableBankingCreateSessionResponseDTO create(EnableBankingAuthenticationRequestDTO request);
}
