package com.pocketwise.application.aspsp.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pocketwise.application.aspsp.dto.EnableBankingAspspDTO;
import com.pocketwise.application.aspsp.service.AspspService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/aspsps")
@RequiredArgsConstructor
@Tag(name = "ASPSPs", description = "Operations related to Account Servicing Payment Service Providers")
class AspspController {

    private final AspspService service;

    @GetMapping
    @Operation(
            summary = "Get all ASPSPs grouped by country",
            description = "Retrieves all available ASPSPs grouped by their country code.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "ASPSPs retrieved"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    Map<String, List<EnableBankingAspspDTO>> findAll() {
        return service.findAll();
    }
}
