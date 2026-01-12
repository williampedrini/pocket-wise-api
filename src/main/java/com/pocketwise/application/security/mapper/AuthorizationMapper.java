package com.pocketwise.application.security.mapper;

import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import jakarta.annotation.Nonnull;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.pocketwise.application.aspsp.mapper.AspspMapper;
import com.pocketwise.application.security.dto.AuthorizationDTO;
import com.pocketwise.application.security.dto.AuthorizationRequestAccessDTO;
import com.pocketwise.application.security.dto.AuthorizationRequestDTO;

@Mapper(uses = {AspspMapper.class})
public interface AuthorizationMapper {

    /**
     * Maps an {@link AuthorizationDTO} and a callback URL to an {@link AuthorizationRequestDTO}.
     *
     * @param value the source {@link AuthorizationDTO} containing authorization data
     * @param callbackUrl the URL to be used for redirection
     * @return an {@link AuthorizationRequestDTO} containing the mapped data
     */
    @Mappings({
        @Mapping(target = "redirectUrl", source = "callbackUrl"),
        @Mapping(target = "psuType", constant = "personal"),
        @Mapping(target = "state", expression = "java(java.util.UUID.randomUUID())"),
        @Mapping(target = "credentialsAutosubmit", constant = "false"),
        @Mapping(target = "aspsp", source = "value"),
        @Mapping(target = "access", expression = "java(access())"),
    })
    AuthorizationRequestDTO map(AuthorizationDTO value, String callbackUrl);

    /**
     * Generates an {@link AuthorizationRequestAccessDTO} containing access permissions for balances and transactions,
     * valid for a duration of 90 days from the current date.
     *
     * @return an {@link AuthorizationRequestAccessDTO} configured with default access settings and expiration date
     */
    @Nonnull
    default AuthorizationRequestAccessDTO access() {
        final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'");
        final String expirationDate = ZonedDateTime.now().plusDays(90).format(formatter);
        return AuthorizationRequestAccessDTO.builder()
                .balances(true)
                .transactions(true)
                .validUntil(expirationDate)
                .build();
    }
}
