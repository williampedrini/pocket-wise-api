package com.pocketwise.application.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record AspspDTO(@JsonProperty("name") String name, @JsonProperty("country") String country) {}
