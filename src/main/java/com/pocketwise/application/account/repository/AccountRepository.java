package com.pocketwise.application.account.repository;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pocketwise.application.account.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {

    /**
     * Retrieves the account associated with the provided UUID.
     *
     * @param uuid the unique identifier of the account to search for; must not be null.
     * @return an {@link Optional} containing the {@link Account} associated with the given UUID,
     *         or an empty {@link Optional} if no such account exists.
     */
    Optional<Account> findByUuid(UUID uuid);

    /**
     * Retrieves all accounts associated with the given session email.
     *
     * @param email the email associated with the session; must not be null.
     * @return a collection of {@link Account} objects linked to the session email;
     *         an empty collection is returned if no accounts are found.
     */
    Collection<Account> findAllBySessionEmail(String email);
}
