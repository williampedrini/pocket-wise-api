package com.pocketwise.application.aspsp.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record AspspListDTO(@JsonProperty("aspsps") List<AspspDTO> aspsps) {}
