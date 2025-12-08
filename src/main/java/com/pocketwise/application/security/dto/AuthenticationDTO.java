package com.pocketwise.application.security.dto;

import java.util.UUID;

import lombok.Builder;

@Builder
public record AuthenticationDTO(UUID code, UUID state) {}
