package com.pocketwise.application.account.dto;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EnableBankingAccountBalanceWrapperDTO(
        @JsonProperty("balances") Collection<EnableBankingAccountBalanceDTO> balances) {}
