package com.pocketwise.application.account.service;

import java.util.Collection;
import java.util.UUID;

import jakarta.annotation.Nonnull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.pocketwise.application.account.dto.TransactionDTO;
import com.pocketwise.application.account.entity.Account;
import com.pocketwise.application.account.entity.Transaction;
import com.pocketwise.application.account.mapper.TransactionMapper;
import com.pocketwise.application.account.repository.TransactionRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionMapper mapper;
    private final TransactionRepository repository;

    /**
     * Persists a transaction associated with the given account if it doesn't already exist.
     *
     * @param account the account to associate the transaction with; must not be null.
     * @param transaction the transaction DTO to persist; must not be null.
     */
    @Transactional
    public void createIfNotExists(@Nonnull final Account account, @Nonnull final TransactionDTO transaction) {
        Assert.notNull(account, "The account is mandatory.");
        Assert.notNull(transaction, "The transaction is mandatory.");
        if (repository.existsByTransactionId(transaction.transactionId())) {
            return;
        }
        final Transaction entity = mapper.map(transaction);
        entity.setAccount(account);
        repository.save(entity);
    }

    /**
     * Persists multiple transactions associated with the given account, skipping those that already exist.
     *
     * @param account the account to associate the transactions with; must not be null.
     * @param transactions the collection of transaction DTOs to persist; must not be null.
     */
    @Transactional
    public void createAllIfNotExists(
            @Nonnull final Account account, @Nonnull final Collection<TransactionDTO> transactions) {
        Assert.notNull(account, "The account is mandatory.");
        Assert.notNull(transactions, "The transactions collection is mandatory.");
        transactions.forEach(transaction -> createIfNotExists(account, transaction));
    }

    /**
     * Retrieves all transactions for the given account UUID.
     *
     * @param accountUuid the UUID of the account; must not be null.
     * @return a collection of transaction DTOs.
     */
    @Nonnull
    public Collection<TransactionDTO> findAllByAccountUuid(@Nonnull final UUID accountUuid) {
        Assert.notNull(accountUuid, "The account UUID is mandatory.");
        return repository.findAllByAccountUuid(accountUuid).stream()
                .map(mapper::map)
                .toList();
    }
}
