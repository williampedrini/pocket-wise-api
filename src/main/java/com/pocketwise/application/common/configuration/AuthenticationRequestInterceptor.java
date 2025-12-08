package com.pocketwise.application.common.configuration;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import org.springframework.stereotype.Component;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
class AuthenticationRequestInterceptor implements RequestInterceptor {

    private final JwtTokenProvider provider;

    @Override
    public void apply(final RequestTemplate template) {
        final String token = provider.generateToken();
        template.header(AUTHORIZATION, "Bearer %s".formatted(token));
        template.header(CONTENT_TYPE, APPLICATION_JSON.toString());
    }
}
