package com.pocketwise.application.security.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record AuthorizationRequestDTO(
        @JsonProperty("access") AuthorizationRequestAccessDTO access,
        @JsonProperty("aspsp") AspspDTO aspsp,
        @JsonProperty("psu_type") String psuType,
        @JsonProperty("redirect_url") String redirectUrl,
        @JsonProperty("state") UUID state,
        @JsonProperty("auth_method") String authMethod,
        @JsonProperty("credentials") Object credentials,
        @JsonProperty("credentials_autosubmit") boolean credentialsAutosubmit,
        @JsonProperty("language") String language,
        @JsonProperty("psu_id") String psuId) {}
