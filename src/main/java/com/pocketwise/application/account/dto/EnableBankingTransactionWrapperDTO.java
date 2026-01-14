package com.pocketwise.application.account.dto;

import static java.util.Objects.requireNonNullElse;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.annotation.Nonnull;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record EnableBankingTransactionWrapperDTO(
        @JsonProperty("transactions") Collection<EnableBankingTransactionDTO> transactions,
        @JsonProperty("continuation_key") String continuation) {

    @Nonnull
    public Collection<EnableBankingTransactionDTO> transactions() {
        return requireNonNullElse(transactions, new ArrayList<>());
    }
}
