package com.pocketwise.application.account.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.pocketwise.application.account.dto.EnableBankingAccountBalanceWrapperDTO;
import com.pocketwise.application.account.dto.EnableBankingAccountDTO;
import com.pocketwise.application.account.dto.EnableBankingTransactionWrapperDTO;
import com.pocketwise.application.common.configuration.FeignClientConfiguration;

@FeignClient(
        name = "accountClient",
        url = "${integration.api.base-url}/accounts",
        configuration = FeignClientConfiguration.class)
public interface AccountClient {

    /**
     * Retrieves the details of the account associated with the specified unique account ID.
     *
     * @param id the unique identifier of the account to be retrieved; must not be null.
     * @return an {@link EnableBankingAccountDTO} containing the details of the account associated with the specified ID.
     */
    @GetMapping("/{id}/details")
    EnableBankingAccountDTO findById(@PathVariable UUID id);

    /**
     * Retrieves all account balances associated with the account linked to the specified unique account ID.
     *
     * @param id the unique identifier of the account to fetch balances for; must not be null.
     * @return an {@link EnableBankingAccountBalanceWrapperDTO} containing all balances associated with the specified account ID.
     */
    @GetMapping("/{id}/balances")
    EnableBankingAccountBalanceWrapperDTO findAllBalancesByAccountId(@PathVariable UUID id);

    /**
     * Retrieves transactions for the account associated with the specified unique account ID.
     *
     * @param id the unique identifier of the account to fetch transactions for; must not be null.
     * @param dateFrom the start date for filtering transactions in ISO 8601 format (YYYY-MM-DD); optional.
     * @param dateTo the end date for filtering transactions in ISO 8601 format (YYYY-MM-DD); optional.
     * @param continuationKey the pagination key for fetching the next page of results; optional.
     * @return a {@link EnableBankingTransactionWrapperDTO} containing the transactions and pagination information.
     */
    @GetMapping("/{id}/transactions")
    EnableBankingTransactionWrapperDTO findAllTransactionsByAccountId(
            @PathVariable UUID id,
            @RequestParam(value = "date_from", required = false) String dateFrom,
            @RequestParam(value = "date_to", required = false) String dateTo,
            @RequestParam(value = "continuation_key", required = false) String continuationKey);
}
