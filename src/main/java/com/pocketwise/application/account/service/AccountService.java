package com.pocketwise.application.account.service;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.Collection;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.pocketwise.application.account.client.AccountClient;
import com.pocketwise.application.account.dto.*;
import com.pocketwise.application.account.entity.Account;
import com.pocketwise.application.account.mapper.AccountMapper;
import com.pocketwise.application.account.repository.AccountRepository;
import com.pocketwise.application.security.dto.UserDTO;
import com.pocketwise.application.security.entity.Session;
import com.pocketwise.application.security.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountClient client;
    private final AccountMapper mapper;
    private final UserService userService;
    private final AccountRepository repository;

    /**
     * Retrieves the account information corresponding to the provided IBAN.
     *
     * @param iban the International Bank Account Number (IBAN) used to search for the account; must not be null or empty.
     * @return an {@link AccountDTO} containing the account information associated with the given IBAN.
     * @throws IllegalArgumentException if the IBAN is not found, or if the provided IBAN is null or empty.
     */
    @Nonnull
    public AccountDTO findByIban(@Nonnull final String iban) {
        Assert.hasText(iban, "The IBAN is mandatory.");
        final Account account = repository
                .findTopByIbanOrderByCreatedAtDesc(iban)
                .orElseThrow(() -> {
                    final String message = String.format("The IBAN is not found for %s", iban);
                    return new IllegalArgumentException(message);
                });
        final UserDTO sessionUser = userService.getSessionUser();
        if (sessionUser.email().equalsIgnoreCase(account.getSession().getEmail())) {
            return client.findById(account.getUuid());
        }
        final String message = "The session user is not allowed to access this account.";
        throw new IllegalStateException(message);
    }

    /**
     * Retrieves all account balances associated with the account linked to the provided IBAN.
     *
     * @param iban the International Bank Account Number (IBAN) used to search for the associated account; must not be null or empty.
     * @return an {@link AccountBalanceWrapperDTO} containing all balances related to the account associated with the given IBAN.
     * @throws IllegalArgumentException if the IBAN is not found, or if the provided IBAN is null or empty.
     */
    @Nonnull
    public Collection<AccountBalanceDTO> findAllBalancesByIban(@Nonnull final String iban) {
        Assert.hasText(iban, "The IBAN is mandatory.");
        final Account account = repository
                .findTopByIbanOrderByCreatedAtDesc(iban)
                .orElseThrow(() -> {
                    final String message = String.format("The IBAN is not found for %s", iban);
                    return new IllegalArgumentException(message);
                });
        final UserDTO sessionUser = userService.getSessionUser();
        if (sessionUser.email().equalsIgnoreCase(account.getSession().getEmail())) {
            return client.findAllBalancesByAccountId(account.getUuid()).balances();
        }
        final String message = "The session user is not allowed to access this account.";
        throw new IllegalStateException(message);
    }

    /**
     * Creates a new account based on the provided account data transfer object (DTO).
     *
     * @param account the account data transfer object to be created; must not be null.
     */
    @Transactional
    public void create(@Nonnull final Session session, @Nonnull final AccountDTO account) {
        Assert.notNull(session, "The session is mandatory");
        Assert.notNull(account, "The account is mandatory");
        final Account entity = mapper.map(account);
        entity.setSession(session);
        repository.save(entity);
    }

    /**
     * Retrieves all transactions for the account associated with the provided IBAN,
     * automatically handling pagination to fetch all available transactions.
     *
     * @param iban the International Bank Account Number (IBAN) used to search for the associated account; must not be null or empty.
     * @param from the start date for filtering transactions in ISO 8601 format (YYYY-MM-DD); optional.
     * @param to the end date for filtering transactions in ISO 8601 format (YYYY-MM-DD); optional.
     * @return a {@link TransactionResponseDTO} containing all transactions with hasMore set to false.
     * @throws IllegalArgumentException if the IBAN is not found, or if the provided IBAN is null or empty.
     */
    @Nonnull
    public Collection<TransactionDTO> findAllTransactionsByIban(
            @Nonnull final String iban, @Nullable final String from, @Nullable final String to) {
        Assert.hasText(iban, "The IBAN is mandatory.");
        log.debug("Fetching all transactions (with auto-pagination) for IBAN: {}, from: {}, to: {}", iban, from, to);

        final Account account = findAccountByIban(iban);
        final Collection<TransactionDTO> transactions = new ArrayList<>();
        String continuation = null;

        do {
            final TransactionWrapperDTO wrapper =
                    client.findAllTransactionsByAccountId(account.getUuid(), from, to, continuation);
            transactions.addAll(wrapper.transactions());
            continuation = wrapper.continuation();
        } while (isNotBlank(continuation));

        log.debug("Total transactions fetched for IBAN {}: {}", iban, transactions.size());
        return transactions;
    }

    /**
     * Finds the account entity by IBAN.
     *
     * @param iban the International Bank Account Number (IBAN) to search for.
     * @return the {@link Account} entity associated with the given IBAN.
     * @throws IllegalArgumentException if the IBAN is not found.
     */
    private Account findAccountByIban(@Nonnull final String iban) {
        return repository.findTopByIbanOrderByCreatedAtDesc(iban).orElseThrow(() -> {
            final String message = String.format("The IBAN is not found for %s", iban);
            return new IllegalArgumentException(message);
        });
    }
}
