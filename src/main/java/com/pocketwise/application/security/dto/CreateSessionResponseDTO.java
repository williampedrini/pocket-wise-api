package com.pocketwise.application.security.dto;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pocketwise.application.account.dto.AccountDTO;

public record CreateSessionResponseDTO(
        @JsonProperty("session_id") String sessionId, @JsonProperty("accounts") Collection<AccountDTO> accounts) {}
