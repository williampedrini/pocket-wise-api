package com.pocketwise.application.security.dto;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GetSessionResponseAccountDataDTO(
        @JsonProperty("uid") String uid,
        @JsonProperty("identification_hash") String identificationHash,
        @JsonProperty("identification_hashes") Collection<String> identificationHashes) {}
