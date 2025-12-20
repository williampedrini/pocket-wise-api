package com.pocketwise.application.security.service;

import static java.util.Optional.ofNullable;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;

import com.pocketwise.application.security.dto.UserDTO;
import com.pocketwise.application.security.mapper.UserMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper mapper;

    /**
     * Returns the currently authenticated principal injected by Spring Security.
     *
     * @return the authenticated principal
     */
    public UserDTO getSessionUser() {
        return ofNullable(SecurityContextHolder.getContext().getAuthentication())
                .filter(JwtAuthenticationToken.class::isInstance)
                .map(JwtAuthenticationToken.class::cast)
                .map(AbstractOAuth2TokenAuthenticationToken::getToken)
                .map(Jwt::getClaims)
                .map(mapper::map)
                .orElseThrow(() -> {
                    final String message = "No session user found.";
                    return new IllegalStateException(message);
                });
    }
}
