package com.pocketwise.application.account.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record TransactionPartyDTO(
        @JsonProperty("name") String name, @JsonProperty("postal_address") TransactionPostalAddressDTO postalAddress) {}
