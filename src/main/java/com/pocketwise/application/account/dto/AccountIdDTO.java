package com.pocketwise.application.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AccountIdDTO(@JsonProperty("iban") String iban) {}
