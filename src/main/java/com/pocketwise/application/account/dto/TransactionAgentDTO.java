package com.pocketwise.application.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record TransactionAgentDTO(
        @JsonProperty("bic_fi") String bicFi,
        @JsonProperty("clearing_system_member_id") TransactionClearingSystemMemberIdDTO clearingSystemMemberId,
        @JsonProperty("name") String name) {}
