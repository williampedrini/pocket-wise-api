package com.pocketwise.application.security.dto;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetSessionResponseAccessDTO(
        @JsonProperty("accounts") Collection<String> accounts,
        @JsonProperty("balances") Boolean balances,
        @JsonProperty("transactions") Boolean transactions,
        @JsonProperty("valid_until") String validUntil) {}
