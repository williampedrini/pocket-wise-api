package com.pocketwise.application.security.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pocketwise.application.security.dto.UserDTO;
import com.pocketwise.application.security.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@PreAuthorize("isAuthenticated()")
@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "Users", description = "Operations related to the authenticated user")
class UserController {

    private final UserService userService;

    @GetMapping("/me")
    @Operation(
            summary = "Get authenticated user",
            description = "Returns the attributes of the currently authenticated OAuth2 user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User attributes returned"),
        @ApiResponse(responseCode = "401", description = "Unauthorized: user not authenticated"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    UserDTO getSessionUser() {
        return userService.getSessionUser();
    }
}
