package com.pocketwise.application.account.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record EnableBankingTransactionPostalAddressDTO(
        @JsonProperty("address_line") List<String> addressLine,
        @JsonProperty("address_type") String addressType,
        @JsonProperty("building_number") String buildingNumber,
        @JsonProperty("country") String country,
        @JsonProperty("country_sub_division") String countrySubDivision,
        @JsonProperty("department") String department,
        @JsonProperty("post_code") String postCode,
        @JsonProperty("street_name") String streetName,
        @JsonProperty("sub_department") String subDepartment,
        @JsonProperty("town_name") String townName) {}
