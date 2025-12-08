package com.pocketwise.application.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AuthorizationResponseDTO(@JsonProperty("url") String url, @JsonProperty("authorization_id") String id) {}
