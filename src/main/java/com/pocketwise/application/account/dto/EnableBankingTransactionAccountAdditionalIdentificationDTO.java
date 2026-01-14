package com.pocketwise.application.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record EnableBankingTransactionAccountAdditionalIdentificationDTO(
        @JsonProperty("identification") String identification, @JsonProperty("scheme_name") String schemeName) {}
