package com.pocketwise.application.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record TransactionExchangeRateDTO(
        @JsonProperty("unit_currency") String unitCurrency,
        @JsonProperty("exchange_rate") String exchangeRate,
        @JsonProperty("rate_type") String rateType,
        @JsonProperty("contract_identification") String contractIdentification,
        @JsonProperty("instructed_amount") TransactionAmountDTO instructedAmount) {}
