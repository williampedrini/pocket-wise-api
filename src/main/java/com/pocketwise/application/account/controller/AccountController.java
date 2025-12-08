package com.pocketwise.application.account.controller;

import java.util.Collection;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.pocketwise.application.account.dto.AccountBalanceDTO;
import com.pocketwise.application.account.dto.AccountDTO;
import com.pocketwise.application.account.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

// @PreAuthorize("isAuthenticated()")
@Slf4j
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Operations related to bank accounts")
class AccountController {

    private final AccountService service;

    @GetMapping("/{iban}")
    @Operation(summary = "Get account by IBAN", description = "Retrieves account details for the given account IBAN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Account retrieved"),
        @ApiResponse(responseCode = "400", description = "Invalid account ID provided"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    AccountDTO findByIban(@Parameter(description = "Account IBAN", required = true) @PathVariable String iban) {
        return service.findByIban(iban);
    }

    @GetMapping("/{iban}/balances")
    @Operation(
            summary = "Get account balances",
            description = "Retrieves all balances associated with the given account IBAN.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Balances retrieved"),
        @ApiResponse(responseCode = "400", description = "Invalid account ID provided"),
        @ApiResponse(responseCode = "404", description = "Account or balances not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    Collection<AccountBalanceDTO> findAllBalancesByIban(
            @Parameter(description = "Account IBAN", required = true) @PathVariable String iban) {
        return service.findAllBalancesByIban(iban);
    }
}
