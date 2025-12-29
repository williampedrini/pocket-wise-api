package com.pocketwise.application.account.repository;

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
}
