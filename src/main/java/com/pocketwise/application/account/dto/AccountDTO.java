package com.pocketwise.application.account.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountDTO(
        @JsonProperty("account_id") AccountIdDTO accountId,
        @JsonProperty("account_servicer") AccountServicerDTO accountServicer,
        @JsonProperty("name") String name,
        @JsonProperty("details") String details,
        @JsonProperty("usage") String usage,
        @JsonProperty("cash_account_type") String cashAccountType,
        @JsonProperty("product") String product,
        @JsonProperty("currency") String currency,
        @JsonProperty("psu_status") String psuStatus,
        @JsonProperty("credit_limit") String creditLimit,
        @JsonProperty("postal_address") Object postalAddress,
        @JsonProperty("uid") UUID uid) {}
