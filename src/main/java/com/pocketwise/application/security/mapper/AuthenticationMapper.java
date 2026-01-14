package com.pocketwise.application.security.mapper;

import org.mapstruct.Mapper;

import com.pocketwise.application.security.dto.AuthenticationDTO;
import com.pocketwise.application.security.dto.EnableBankingAuthenticationRequestDTO;

@Mapper
public interface AuthenticationMapper {

    /**
     * Maps an {@link AuthenticationDTO} to an {@link EnableBankingAuthenticationRequestDTO}.
     *
     * @param value the source {@link AuthenticationDTO} containing authentication data
     * @return an {@link EnableBankingAuthenticationRequestDTO} containing the mapped data
     */
    EnableBankingAuthenticationRequestDTO map(AuthenticationDTO value);
}
