package com.pocketwise.application.security.dto;

import lombok.Builder;

@Builder
public record AuthorizationDTO(String bank, String country) {}
