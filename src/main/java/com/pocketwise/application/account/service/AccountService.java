package com.pocketwise.application.account.service;

import java.util.Collection;

import jakarta.annotation.Nonnull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.pocketwise.application.account.client.AccountClient;
import com.pocketwise.application.account.dto.AccountBalanceDTO;
import com.pocketwise.application.account.dto.AccountBalanceWrapperDTO;
import com.pocketwise.application.account.dto.AccountDTO;
import com.pocketwise.application.account.entity.Account;
import com.pocketwise.application.account.mapper.AccountMapper;
import com.pocketwise.application.account.repository.AccountRepository;
import com.pocketwise.application.security.entity.Session;
import com.pocketwise.application.security.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountClient client;
    private final AccountMapper mapper;
    private final AccountRepository repository;
    private final UserService userService;

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
        //        final UserDTO sessionUser = userService.getSessionUser();
        //        if (sessionUser.email().equalsIgnoreCase(account.getSession().getEmail())) {
        return client.findById(account.getUuid());
        //        }
        //        final String message = "The session user is not allowed to access this account.";
        //        throw new IllegalStateException(message);
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
        //        final UserDTO sessionUser = userService.getSessionUser();
        //        if (sessionUser.email().equalsIgnoreCase(account.getSession().getEmail())) {
        return client.findAllBalancesById(account.getUuid()).balances();
        //        }
        //        final String message = "The session user is not allowed to access this account.";
        //        throw new IllegalStateException(message);
    }

    /**
     * Creates a new account based on the provided account data transfer object (DTO).
     *
     * @param account the account data transfer object to be created; must not be null.
     * @return the created account as a data transfer object.
     */
    @Transactional
    public AccountDTO create(@Nonnull final Session session, @Nonnull final AccountDTO account) {
        Assert.notNull(session, "The session is mandatory");
        Assert.notNull(account, "The account is mandatory");
        final Account entity = mapper.map(account);
        entity.setSession(session);
        final Account persistedEntity = repository.save(entity);
        return mapper.map(persistedEntity);
    }
}
