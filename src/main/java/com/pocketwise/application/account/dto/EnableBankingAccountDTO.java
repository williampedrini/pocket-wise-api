package com.pocketwise.application.account.dto;

import java.io.Serializable;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EnableBankingAccountDTO(
        @JsonProperty("account_id") EnableBankingAccountIdDTO accountId,
        @JsonProperty("account_servicer") EnableBankingAccountServicerDTO accountServicer,
        @JsonProperty("name") String name,
        @JsonProperty("details") String details,
        @JsonProperty("usage") String usage,
        @JsonProperty("cash_account_type") String cashAccountType,
        @JsonProperty("product") String product,
        @JsonProperty("currency") String currency,
        @JsonProperty("psu_status") String psuStatus,
        @JsonProperty("credit_limit") String creditLimit,
        @JsonProperty("postal_address") Object postalAddress,
        @JsonProperty("uid") UUID uid)
        implements Serializable {}
