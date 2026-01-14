package com.pocketwise.application.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EnableBankingAccountBalanceDTO(
        @JsonProperty("name") String name,
        @JsonProperty("balance_amount") EnableBankingAccountBalanceAmountDTO balanceAmount,
        @JsonProperty("balance_type") String balanceType) {}
