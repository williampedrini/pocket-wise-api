package com.pocketwise.application.account.repository;

import java.util.Collection;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pocketwise.application.account.entity.Transaction;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    /**
     * Retrieves all transactions associated with a given account UUID.
     *
     * @param accountUuid the unique identifier of the account.
     * @return a collection of transactions for the specified account.
     */
    Collection<Transaction> findAllByAccountUuid(UUID accountUuid);

    /**
     * Checks if a transaction exists with the given transaction ID.
     *
     * @param transactionId the external transaction ID from the API.
     * @return true if a transaction with the given ID exists, false otherwise.
     */
    boolean existsByTransactionId(String transactionId);
}
