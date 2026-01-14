package com.pocketwise.application.account.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EnableBankingAccountServicerDTO(
        @JsonProperty("bic_fi") String bicFi,
        @JsonProperty("clearing_system_member_id") String clearingSystemMemberId,
        @JsonProperty("name") String name)
        implements Serializable {}
