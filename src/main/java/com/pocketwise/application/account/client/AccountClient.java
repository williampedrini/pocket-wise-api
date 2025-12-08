package com.pocketwise.application.account.client;

import java.util.UUID;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.pocketwise.application.account.dto.AccountBalanceWrapperDTO;
import com.pocketwise.application.account.dto.AccountDTO;
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
     * @return an {@link AccountDTO} containing the details of the account associated with the specified ID.
     */
    @GetMapping("/{id}/details")
    AccountDTO findById(@PathVariable UUID id);

    /**
     * Retrieves all account balances associated with the account linked to the specified unique account ID.
     *
     * @param id the unique identifier of the account to fetch balances for; must not be null.
     * @return an {@link AccountBalanceWrapperDTO} containing all balances associated with the specified account ID.
     */
    @GetMapping("/{id}/balances")
    AccountBalanceWrapperDTO findAllBalancesById(@PathVariable UUID id);
}
