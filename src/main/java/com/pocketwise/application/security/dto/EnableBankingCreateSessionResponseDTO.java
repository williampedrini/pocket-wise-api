package com.pocketwise.application.security.dto;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pocketwise.application.account.dto.EnableBankingAccountDTO;

public record EnableBankingCreateSessionResponseDTO(
        @JsonProperty("session_id") String sessionId,
        @JsonProperty("accounts") Collection<EnableBankingAccountDTO> accounts) {}
