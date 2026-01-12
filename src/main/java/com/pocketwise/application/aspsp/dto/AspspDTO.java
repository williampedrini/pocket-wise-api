package com.pocketwise.application.aspsp.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record AspspDTO(
        @JsonProperty("name") String name,
        @JsonIgnore @JsonProperty("country") String country,
        @JsonProperty("logo") String logo,
        @JsonProperty("bic") String bic) {}
