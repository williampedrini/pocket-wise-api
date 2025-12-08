package com.pocketwise.application.security.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.UUID;

import jakarta.persistence.*;

import com.pocketwise.application.account.entity.Account;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "SESSION")
public class Session {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "UUID", unique = true, nullable = false)
    private UUID uuid;

    @Column(name = "EMAIL", nullable = false)
    private String email;

    @Column(name = "TOKEN", unique = true, nullable = false)
    private String token;

    @OneToMany(mappedBy = "session", fetch = FetchType.LAZY)
    private Collection<Account> accounts;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void onCreate() {
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
