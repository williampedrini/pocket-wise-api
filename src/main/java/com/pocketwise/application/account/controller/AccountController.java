package com.pocketwise.application.account.controller;

import static org.springframework.http.HttpStatus.NO_CONTENT;

import java.util.Collection;
import java.util.UUID;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.pocketwise.application.account.dto.AccountBalanceDTO;
import com.pocketwise.application.account.dto.AccountDTO;
import com.pocketwise.application.account.dto.EnableBankingAccountDTO;
import com.pocketwise.application.account.dto.TransactionDTO;
import com.pocketwise.application.account.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@PreAuthorize("isAuthenticated()")
@Slf4j
@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
@Tag(name = "Accounts", description = "Operations related to bank accounts")
class AccountController {

    private final AccountService service;

    @GetMapping
    @Operation(
            summary = "Get accounts by session user",
            description = "Retrieves all account details for the given session user.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Accounts retrieved"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    Collection<AccountDTO> findAllBySessionUser() {
        return service.findAllBySessionUser();
    }

    @GetMapping("/{uuid}")
    @Operation(summary = "Get account by UUID", description = "Retrieves account details for the given account UUID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Account retrieved"),
        @ApiResponse(responseCode = "400", description = "Invalid account UUID provided"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    EnableBankingAccountDTO findByUuid(
            @Parameter(description = "Account UUID", required = true) @PathVariable UUID uuid) {
        return service.findByUuid(uuid);
    }

    @GetMapping("/{uuid}/balances")
    @Operation(
            summary = "Get account balances",
            description = "Retrieves all balances associated with the given account UUID.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Balances retrieved"),
        @ApiResponse(responseCode = "400", description = "Invalid account UUID provided"),
        @ApiResponse(responseCode = "404", description = "Account or balances not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    Collection<AccountBalanceDTO> findAllBalancesByUuid(
            @Parameter(description = "Account UUID", required = true) @PathVariable UUID uuid) {
        return service.findAllBalancesByUuid(uuid);
    }

    @GetMapping("/{uuid}/transactions")
    @Operation(
            summary = "Get account transactions",
            description = "Retrieves transactions associated with the given account UUID. "
                    + "Supports optional date filtering and pagination via continuation key.")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Transactions retrieved"),
        @ApiResponse(responseCode = "400", description = "Invalid account UUID or date format provided"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    Collection<TransactionDTO> findAllTransactionsByUuid(
            @Parameter(description = "Account UUID", required = true) @PathVariable UUID uuid,
            @Parameter(description = "Start date for filtering transactions (ISO 8601 format: YYYY-MM-DD)")
                    @RequestParam(required = false)
                    String from,
            @Parameter(description = "End date for filtering transactions (ISO 8601 format: YYYY-MM-DD)")
                    @RequestParam(required = false)
                    String to) {
        return service.findAllTransactionsByUuid(uuid, from, to);
    }

    @DeleteMapping("/{uuid}")
    @ResponseStatus(NO_CONTENT)
    @Operation(summary = "Delete account by UUID", description = "Deletes the account for the given account UUID.")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Account deleted"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "Account not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    void deleteByUuid(@Parameter(description = "Account UUID", required = true) @PathVariable UUID uuid) {
        service.deleteByUuid(uuid);
    }
}
