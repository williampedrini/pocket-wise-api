package com.pocketwise.application.account.dto;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

/**
 * Response DTO for transaction list endpoints, including pagination information.
 */
@Builder
public record TransactionResponseDTO(
        @JsonProperty("transactions") Collection<TransactionDTO> transactions,
        @JsonProperty("hasMore") boolean hasMore,
        @JsonProperty("page") String page) {}
