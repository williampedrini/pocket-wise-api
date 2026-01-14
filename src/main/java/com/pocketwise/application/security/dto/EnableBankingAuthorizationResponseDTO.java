package com.pocketwise.application.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EnableBankingAuthorizationResponseDTO(
        @JsonProperty("url") String url, @JsonProperty("authorization_id") String id) {}
