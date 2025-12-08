package com.pocketwise.application.security.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.pocketwise.application.security.dto.AspspDTO;
import com.pocketwise.application.security.dto.AuthorizationDTO;

@Mapper
interface AspspMapper {

    /**
     * Maps an {@link AuthorizationDTO} to an {@link AspspDTO}.
     *
     * @param value the source object containing bank and country information
     * @return an {@link AspspDTO} containing the mapped name and country
     */
    @Mappings({@Mapping(target = "name", source = "bank")})
    AspspDTO aspsp(AuthorizationDTO value);
}
