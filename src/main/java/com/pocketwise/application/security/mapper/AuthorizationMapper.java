package com.pocketwise.application.security.mapper;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.annotation.Nonnull;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.pocketwise.application.security.dto.AuthorizationDTO;
import com.pocketwise.application.security.dto.EnableBankingAuthorizationRequestAccessDTO;
import com.pocketwise.application.security.dto.EnableBankingAuthorizationRequestDTO;
import com.pocketwise.application.security.dto.EnableBankingAuthorizationRequestDTO.AspspDTO;

@Mapper
public interface AuthorizationMapper {

    /**
     * Maps an {@link AuthorizationDTO} and a callback URL to an {@link EnableBankingAuthorizationRequestDTO}.
     *
     * @param value the source {@link AuthorizationDTO} containing authorization data
     * @param callbackUrl the URL to be used for redirection
     * @return an {@link EnableBankingAuthorizationRequestDTO} containing the mapped data
     */
    @Mappings({
        @Mapping(target = "redirectUrl", source = "callbackUrl"),
        @Mapping(target = "psuType", constant = "personal"),
        @Mapping(target = "state", expression = "java(java.util.UUID.randomUUID())"),
        @Mapping(target = "credentialsAutosubmit", constant = "false"),
        @Mapping(target = "aspsp", source = "value"),
        @Mapping(target = "access", expression = "java(access())"),
    })
    EnableBankingAuthorizationRequestDTO map(AuthorizationDTO value, String callbackUrl);

    /**
     * Maps an {@link AuthorizationDTO} to an {@link AspspDTO}.
     *
     * @param value the source object containing bank and country information
     * @return an {@link AspspDTO} containing the mapped name and country
     */
    @Mappings({@Mapping(target = "name", source = "bank")})
    AspspDTO map(AuthorizationDTO value);

    /**
     * Generates an {@link EnableBankingAuthorizationRequestAccessDTO} containing access permissions for balances and transactions,
     * valid for a duration of 90 days from the current date.
     *
     * @return an {@link EnableBankingAuthorizationRequestAccessDTO} configured with default access settings and expiration date
     */
    @Nonnull
    default EnableBankingAuthorizationRequestAccessDTO access() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        final String expirationDate = ZonedDateTime.now().plusDays(90).format(formatter);
        return EnableBankingAuthorizationRequestAccessDTO.builder()
                .balances(true)
                .transactions(true)
                .validUntil(expirationDate)
                .build();
    }
}
