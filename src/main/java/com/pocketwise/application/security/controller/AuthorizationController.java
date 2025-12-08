package com.pocketwise.application.security.controller;

import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import com.pocketwise.application.security.dto.AuthenticationDTO;
import com.pocketwise.application.security.dto.AuthorizationDTO;
import com.pocketwise.application.security.service.AuthorizationService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authorization")
@Tag(name = "Authorization", description = "Endpoints to initiate and handle bank authorization flow")
class AuthorizationController {

    private final AuthorizationService service;

    @GetMapping("/callback")
    @Operation(
            summary = "Authorization callback",
            description =
                    "Handles the redirection from bank with the authorization code and exchanges it for access tokens.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Callback processed"),
        @ApiResponse(responseCode = "400", description = "Missing or invalid code"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    void handleCallback(
            @Parameter(description = "Authorization code returned by bank")
                    @RequestParam(name = "code", required = false)
                    UUID code,
            @Parameter(description = "Authorization external identifier used by the application for identification")
                    @RequestParam(name = "state", required = false)
                    UUID state) {
        final AuthenticationDTO payload =
                AuthenticationDTO.builder().code(code).state(state).build();
        service.authenticate(payload);
    }

    @GetMapping
    @Operation(
            summary = "Start bank authorization",
            description = "Initiates the bank authorization flow and redirects the user to the bank's consent page.")
    @ApiResponses({
        @ApiResponse(responseCode = "302", description = "Redirect to bank consent URL"),
        @ApiResponse(responseCode = "400", description = "Invalid parameters"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PreAuthorize("isAuthenticated()")
    RedirectView authorize(
            @Parameter(description = "Bank identifier (e.g., bank slug)", required = true) @RequestParam
                    final String bank,
            @Parameter(description = "Country code in ISO 3166-1 alpha-2 format", required = true) @RequestParam
                    final String country) {
        log.info("User requesting to connect bank: {} ({})", bank, country);
        final AuthorizationDTO authorization =
                AuthorizationDTO.builder().bank(bank).country(country).build();
        final String redirectUrl = service.authorize(authorization);
        return new RedirectView(redirectUrl);
    }
}
