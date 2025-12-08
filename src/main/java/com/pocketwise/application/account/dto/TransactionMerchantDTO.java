package com.pocketwise.application.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record TransactionMerchantDTO(
        @JsonProperty("name") String name, @JsonProperty("category_code") String categoryCode) {}
