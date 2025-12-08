package com.pocketwise.application.security.dto;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetSessionResponseDTO(
        @JsonProperty("status") String status,
        @JsonProperty("accounts") Collection<String> accounts,
        @JsonProperty("accounts_data") Collection<GetSessionResponseAccountDataDTO> accountsData,
        @JsonProperty("aspsp") AspspDTO aspsp,
        @JsonProperty("psu_type") String psuType,
        @JsonProperty("psu_id_hash") String psuIdHash,
        @JsonProperty("access") GetSessionResponseAccessDTO access,
        @JsonProperty("created") String created,
        @JsonProperty("authorized") String authorized,
        @JsonProperty("closed") String closed) {}
