package com.pocketwise.application.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserDTO(@JsonProperty("email") String email, @JsonProperty("name") String fullName) {}
