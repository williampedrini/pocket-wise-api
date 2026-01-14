package com.pocketwise.application.security.service;

import static java.util.Objects.requireNonNull;
import static java.util.Objects.requireNonNullElse;

import java.util.ArrayList;

import jakarta.annotation.Nonnull;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.pocketwise.application.account.dto.EnableBankingAccountDTO;
import com.pocketwise.application.account.service.AccountService;
import com.pocketwise.application.security.client.AuthorizationClient;
import com.pocketwise.application.security.client.SessionClient;
import com.pocketwise.application.security.dto.*;
import com.pocketwise.application.security.entity.Session;
import com.pocketwise.application.security.mapper.AuthenticationMapper;
import com.pocketwise.application.security.mapper.AuthorizationMapper;
import com.pocketwise.application.security.mapper.SessionMapper;
import com.pocketwise.application.security.repository.SessionRepository;

@Service
public class AuthorizationService {

    private final SessionClient sessionClient;
    private final AuthorizationClient accountClient;
    private final AccountService accountService;
    private final AuthorizationMapper authorizationMapper;
    private final AuthenticationMapper authenticationMapper;
    private final SessionMapper sessionMapper;
    private final SessionRepository sessionRepository;
    private final String callbackUrl;

    AuthorizationService(
            @Nonnull final SessionClient sessionClient,
            @Nonnull final AuthorizationClient client,
            @Nonnull final AccountService accountService,
            @Nonnull final AuthorizationMapper authorizationMapper,
            @Nonnull final AuthenticationMapper authenticationMapper,
            @Nonnull final SessionMapper sessionMapper,
            @Nonnull final SessionRepository sessionRepository,
            @Value("${integration.api.callback-url}") final String callbackUrl) {
        this.sessionClient = requireNonNull(sessionClient, "The  session client is mandatory");
        this.accountClient = requireNonNull(client, "The client is mandatory");
        this.accountService = requireNonNull(accountService, "The account service is mandatory.");
        this.authorizationMapper = requireNonNull(authorizationMapper, "The authorization mapper is mandatory");
        this.authenticationMapper = requireNonNull(authenticationMapper, "The authentication mapper is mandatory");
        this.sessionRepository = requireNonNull(sessionRepository, "The session repository is mandatory");
        this.callbackUrl = requireNonNull(callbackUrl, "The callback url is mandatory");
        this.sessionMapper = requireNonNull(sessionMapper, "The session mapper is mandatory");
    }

    /**
     * Initiates the authorization process by mapping the provided {@link AuthorizationDTO}
     *
     * @param authorization The {@link AuthorizationDTO} containing the details necessary.
     * @return The URL returned by the authorization response, which can be used to complete the authorization process.
     * @throws IllegalArgumentException if the authorization parameter is null.
     */
    @Nonnull
    @Transactional
    public String authorize(@Nonnull final AuthorizationDTO authorization) {
        Assert.notNull(authorization, "The authorization is mandatory");
        final EnableBankingAuthorizationRequestDTO request = authorizationMapper.map(authorization, callbackUrl);
        final EnableBankingAuthorizationResponseDTO response = accountClient.authorize(request);
        final Session session = sessionMapper.map(request);
        sessionRepository.save(session);
        return response.url();
    }

    /**
     * Authenticates a user based on the provided {@link AuthenticationDTO}.
     *
     * @param authentication The {@link AuthenticationDTO} containing the authentication data.
     * @throws IllegalStateException if no session exists for the provided state.
     * @throws IllegalArgumentException if the authentication parameter is null.
     */
    @Transactional
    public void authenticate(@Nonnull final AuthenticationDTO authentication) {
        Assert.notNull(authentication, "The authentication is mandatory");
        final Session session = this.sessionRepository
                .findByUuid(authentication.state())
                .orElseThrow(() -> {
                    final String message = "There is not any authentication for the provided %s state."
                            .formatted(authentication.state());
                    return new IllegalStateException(message);
                });
        final EnableBankingAuthenticationRequestDTO request = authenticationMapper.map(authentication);
        final EnableBankingCreateSessionResponseDTO response = sessionClient.create(request);
        session.setToken(response.sessionId());
        sessionRepository.save(session);

        requireNonNullElse(response.accounts(), new ArrayList<EnableBankingAccountDTO>())
                .forEach((account) -> accountService.create(session, account));
    }
}
