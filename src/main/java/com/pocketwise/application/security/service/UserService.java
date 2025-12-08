package com.pocketwise.application.security.service;

import static java.util.Optional.ofNullable;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
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
                .filter(OAuth2AuthenticationToken.class::isInstance)
                .map(OAuth2AuthenticationToken.class::cast)
                .map(OAuth2AuthenticationToken::getPrincipal)
                .map(OAuth2User::getAttributes)
                .map(mapper::map)
                .orElseThrow(() -> {
                    final String message = "No session user found.";
                    return new IllegalStateException(message);
                });
    }
}
