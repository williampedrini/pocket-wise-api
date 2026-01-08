package com.pocketwise.application.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountBalanceDTO(
        @JsonProperty("name") String name,
        @JsonProperty("balance_amount") AccountBalanceAmountDTO balanceAmount,
        @JsonProperty("balance_type") String balanceType) {}
