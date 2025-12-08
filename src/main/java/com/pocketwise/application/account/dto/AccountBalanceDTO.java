package com.pocketwise.application.account.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountBalanceDTO(
        @JsonProperty("name") String name,
        @JsonProperty("balance_amount") AccountBalanceAmountDTO balanceAmount,
        @JsonProperty("balance_type") String balanceType,
        @JsonProperty("last_change_date_time") OffsetDateTime lastChangeDateTime,
        @JsonProperty("reference_date") LocalDate referenceDate,
        @JsonProperty("last_committed_transaction") String lastCommittedTransaction) {}
