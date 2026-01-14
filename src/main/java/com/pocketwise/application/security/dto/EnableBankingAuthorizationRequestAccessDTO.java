package com.pocketwise.application.security.dto;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record EnableBankingAuthorizationRequestAccessDTO(
        @JsonProperty("balances") boolean balances,
        @JsonProperty("transactions") boolean transactions,
        @JsonProperty("valid_until") String validUntil,
        @JsonProperty("accounts") Collection<EnableBankingAuthorizationRequestAccountDTO> accounts) {}
