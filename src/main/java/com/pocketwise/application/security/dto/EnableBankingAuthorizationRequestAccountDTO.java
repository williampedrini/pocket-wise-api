package com.pocketwise.application.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EnableBankingAuthorizationRequestAccountDTO(
        @JsonProperty("iban") String iban,
        @JsonProperty("scheme_name") String schemeName,
        @JsonProperty("identification") String identification) {}
