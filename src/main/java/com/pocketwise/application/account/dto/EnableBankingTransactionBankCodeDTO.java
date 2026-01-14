package com.pocketwise.application.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record EnableBankingTransactionBankCodeDTO(
        @JsonProperty("description") String description,
        @JsonProperty("code") String code,
        @JsonProperty("sub_code") String subCode) {}
