package com.pocketwise.application.security.dto;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record EnableBankingAuthorizationRequestDTO(
        @JsonProperty("access") EnableBankingAuthorizationRequestAccessDTO access,
        @JsonProperty("aspsp") AspspDTO aspsp,
        @JsonProperty("psu_type") String psuType,
        @JsonProperty("redirect_url") String redirectUrl,
        @JsonProperty("state") UUID state,
        @JsonProperty("auth_method") String authMethod,
        @JsonProperty("credentials") Object credentials,
        @JsonProperty("credentials_autosubmit") boolean credentialsAutosubmit,
        @JsonProperty("language") String language,
        @JsonProperty("psu_id") String psuId) {

    @Builder
    public record AspspDTO(@JsonProperty("name") String name, @JsonProperty("country") String country)
            implements Serializable {}
}
