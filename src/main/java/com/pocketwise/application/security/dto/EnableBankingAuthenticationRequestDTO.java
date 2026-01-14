package com.pocketwise.application.security.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record EnableBankingAuthenticationRequestDTO(@JsonProperty("code") UUID code) {}
