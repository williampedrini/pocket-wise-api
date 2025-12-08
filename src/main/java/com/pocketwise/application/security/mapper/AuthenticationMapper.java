package com.pocketwise.application.security.mapper;

import org.mapstruct.Mapper;

import com.pocketwise.application.security.dto.AuthenticationDTO;
import com.pocketwise.application.security.dto.AuthenticationRequestDTO;

@Mapper
public interface AuthenticationMapper {

    /**
     * Maps an {@link AuthenticationDTO} to an {@link AuthenticationRequestDTO}.
     *
     * @param value the source {@link AuthenticationDTO} containing authentication data
     * @return an {@link AuthenticationRequestDTO} containing the mapped data
     */
    AuthenticationRequestDTO map(AuthenticationDTO value);
}
