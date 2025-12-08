package com.pocketwise.application.security.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.pocketwise.application.security.dto.AuthorizationRequestDTO;
import com.pocketwise.application.security.entity.Session;

@Mapper(componentModel = "spring")
public interface SessionMapper {

    /**
     * Maps an {@link AuthorizationRequestDTO} to a {@link Session}.
     * The method assigns the request state to the target session's `uuid` field
     * and populates the `email` field from the current session user, resolved via the Spring context
     * without requiring the caller to pass a {@link com.pocketwise.application.security.service.UserService}.
     *
     * @param value the source {@link AuthorizationRequestDTO} containing authorization data
     * @return a {@link Session} entity populated with the data from the provided {@link AuthorizationRequestDTO}
     */
    @Mappings({
        @Mapping(target = "uuid", source = "value.state"),
        @Mapping(
                target = "email",
                expression =
                        "java(com.pocketwise.application.common.configuration.SpringContext.getBean(com.pocketwise.application.security.service.UserService.class).getSessionUser().email())")
    })
    Session map(AuthorizationRequestDTO value);
}
