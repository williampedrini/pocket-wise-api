package com.pocketwise.application.account.service;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

import org.springframework.cache.annotation.Cacheable;
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
    private final TransactionService transactionService;

    /**
     * Retrieves all accounts associated with the current session user.
     *
     * @return a collection of {@link AccountDTO} objects linked to the session user;
     *         an empty collection is returned if no accounts are found.
     */
    @Nonnull
    @Cacheable(cacheNames = "#{@cacheProperties.accounts().name()}", key = "@userService.getSessionUser().email()")
    public Collection<AccountDTO> findAllBySessionUser() {
        final String email = userService.getSessionUser().email();
        return repository.findAllBySessionEmail(email).stream().map(mapper::map).toList();
    }

    /**
     * Retrieves the account information corresponding to the provided UUID.
     *
     * @param uuid the unique identifier used to search for the account; must not be null.
     * @return an {@link AccountDTO} containing the account information associated with the given UUID.
     * @throws IllegalArgumentException if the account is not found, or if the provided UUID is null.
     */
    @Nonnull
    public AccountDTO findByUuid(@Nonnull final UUID uuid) {
        Assert.notNull(uuid, "The UUID is mandatory.");
        final Account account = repository.findByUuid(uuid).orElseThrow(() -> {
            final String message = String.format("The account is not found for %s", uuid);
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
     * Retrieves all account balances associated with the account linked to the provided UUID.
     *
     * @param uuid the unique identifier used to search for the associated account; must not be null.
     * @return an {@link AccountBalanceWrapperDTO} containing all balances related to the account associated with the given UUID.
     * @throws IllegalArgumentException if the account is not found, or if the provided UUID is null.
     */
    @Nonnull
    @Cacheable(cacheNames = "#{@cacheProperties.balances().name()}", key = "#uuid.toString()")
    public Collection<AccountBalanceDTO> findAllBalancesByUuid(@Nonnull final UUID uuid) {
        Assert.notNull(uuid, "The UUID is mandatory.");
        final Account account = repository.findByUuid(uuid).orElseThrow(() -> {
            final String message = String.format("The account is not found for %s", uuid);
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
        final Account entity = repository.findByUuid(account.uid()).orElseGet(() -> mapper.map(account));
        entity.setSession(session);
        repository.save(entity);
    }

    /**
     * Retrieves all transactions for the account associated with the provided UUID,
     * automatically handling pagination to fetch all available transactions and persisting
     * new transactions to the database. Results are cached using the free tier configuration.
     *
     * @param uuid the unique identifier used to search for the associated account; must not be null.
     * @param from the start date for filtering transactions in ISO 8601 format (YYYY-MM-DD); optional.
     * @param to the end date for filtering transactions in ISO 8601 format (YYYY-MM-DD); optional.
     * @return a {@link Collection} containing all transactions.
     * @throws IllegalArgumentException if the account is not found, or if the provided UUID is null.
     */
    @Nonnull
    @Transactional
    @Cacheable(
            cacheNames = "#{@cacheProperties.transactions().free().name()}",
            key = "#uuid.toString() + '-' + #from + '-' + #to")
    public Collection<TransactionDTO> findAllTransactionsByUuid(
            @Nonnull final UUID uuid, @Nullable final String from, @Nullable final String to) {
        Assert.notNull(uuid, "The UUID is mandatory.");
        log.debug("Fetching all transactions (with auto-pagination) for UUID: {}, from: {}, to: {}", uuid, from, to);

        final Account account = findAccountByUuid(uuid);
        final Collection<TransactionDTO> transactions = new ArrayList<>();
        String continuation = null;

        do {
            final TransactionWrapperDTO wrapper =
                    client.findAllTransactionsByAccountId(account.getUuid(), from, to, continuation);
            transactions.addAll(wrapper.transactions());
            continuation = wrapper.continuation();
            transactionService.createAllIfNotExists(account, wrapper.transactions());
        } while (isNotBlank(continuation));

        log.debug("Total transactions fetched for UUID {}: {}", uuid, transactions.size());
        return transactions;
    }

    /**
     * Finds the account entity by UUID.
     *
     * @param uuid the unique identifier to search for.
     * @return the {@link Account} entity associated with the given UUID.
     * @throws IllegalArgumentException if the account is not found.
     */
    private Account findAccountByUuid(@Nonnull final UUID uuid) {
        return repository.findByUuid(uuid).orElseThrow(() -> {
            final String message = String.format("The account is not found for %s", uuid);
            return new IllegalArgumentException(message);
        });
    }
}
