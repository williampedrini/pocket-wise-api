package com.pocketwise.application.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record EnableBankingTransactionClearingSystemMemberIdDTO(
        @JsonProperty("clearing_system_id") String clearingSystemId, @JsonProperty("member_id") Integer memberId) {}
