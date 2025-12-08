package com.pocketwise.application.security.mapper;

import java.util.Map;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.pocketwise.application.security.dto.UserDTO;

@Mapper
public interface UserMapper {

    /**
     * Maps a given Map containing user information to a UserDTO.
     *
     * @param user a map where keys are strings representing the user's attributes
     *             (e.g., "email", "name") and values are the corresponding data
     * @return a UserDTO object populated with the data from the map
     */
    @Mappings({
        @Mapping(target = "email", expression = "java((String) user.get(\"email\"))"),
        @Mapping(target = "fullName", expression = "java((String) user.get(\"name\"))")
    })
    UserDTO map(Map<String, Object> user);
}
