package com.pocketwise.application.account.service;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.Collection;

import jakarta.annotation.Nonnull;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.pocketwise.application.account.dto.EnableBankingTransactionDTO;
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
    public void createIfNotExists(
            @Nonnull final Account account, @Nonnull final EnableBankingTransactionDTO transaction) {
        Assert.notNull(account, "The account is mandatory.");
        Assert.notNull(transaction, "The transaction is mandatory.");
        if (isBlank(transaction.transactionId()) || repository.existsByTransactionId(transaction.transactionId())) {
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
            @Nonnull final Account account, @Nonnull final Collection<EnableBankingTransactionDTO> transactions) {
        Assert.notNull(account, "The account is mandatory.");
        Assert.notNull(transactions, "The transactions collection is mandatory.");
        transactions.forEach(transaction -> createIfNotExists(account, transaction));
    }
}
