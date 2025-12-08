package com.pocketwise.application.security.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

import com.pocketwise.application.common.configuration.FeignClientConfiguration;
import com.pocketwise.application.security.dto.AuthenticationRequestDTO;
import com.pocketwise.application.security.dto.CreateSessionResponseDTO;

@FeignClient(
        name = "sessionClient",
        url = "${integration.api.base-url}/sessions",
        configuration = FeignClientConfiguration.class)
public interface SessionClient {

    /**
     * Creates a new session based on the provided authentication request.
     *
     * @param request The {@link AuthenticationRequestDTO} containing the required authentication details.
     * @return A {@link CreateSessionResponseDTO} containing the details of the newly created session.
     */
    @PostMapping
    CreateSessionResponseDTO create(AuthenticationRequestDTO request);
}
