package com.pocketwise.application.security.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pocketwise.application.security.entity.Session;

public interface SessionRepository extends JpaRepository<Session, Long> {

    /**
     * Retrieves a {@link Session} based on the provided UUID.
     *
     * @param uuid the unique identifier of the session to retrieve
     * @return an {@code Optional} containing the {@link Session} if found.
     */
    Optional<Session> findByUuid(UUID uuid);
}
