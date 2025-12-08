package com.pocketwise.application.account.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pocketwise.application.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Retrieves the most recently created account associated with the provided IBAN.
     *
     * @param iban the International Bank Account Number (IBAN) to search for; must not be null or empty.
     * @return an {@link Optional} containing the most recently created {@link Account} associated with the given IBAN,
     *         or an empty {@link Optional} if no such account exists.
     */
    Optional<Account> findTopByIbanOrderByCreatedAtDesc(String iban);
}
