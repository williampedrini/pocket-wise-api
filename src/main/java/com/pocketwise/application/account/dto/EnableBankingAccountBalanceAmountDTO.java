package com.pocketwise.application.account.dto;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EnableBankingAccountBalanceAmountDTO(
        @JsonProperty("currency") String currency, @JsonProperty("amount") BigDecimal amount) {}
