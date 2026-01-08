package com.pocketwise.application.account.entity;

import static jakarta.persistence.GenerationType.IDENTITY;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import jakarta.persistence.*;

import com.pocketwise.application.common.converter.EncryptedStringConverter;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "TRANSACTION")
public class Transaction {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "UUID", unique = true, nullable = false)
    private UUID uuid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;

    @Column(name = "TRANSACTION_ID", unique = true, nullable = false)
    private String transactionId;

    @Column(name = "MERCHANT_CATEGORY_CODE")
    private String merchantCategoryCode;

    @Column(name = "AMOUNT", nullable = false)
    private BigDecimal amount;

    @Column(name = "CURRENCY", nullable = false)
    private String currency;

    @Column(name = "CREDIT_DEBIT_INDICATOR", nullable = false)
    private String creditDebitIndicator;

    @Column(name = "STATUS")
    private String status;

    @Column(name = "BOOKING_DATE")
    private LocalDate bookingDate;

    @Column(name = "VALUE_DATE")
    private LocalDate valueDate;

    @Column(name = "TRANSACTION_DATE")
    private LocalDate transactionDate;

    @Column(name = "CREDITOR_NAME")
    private String creditorName;

    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "CREDITOR_IBAN")
    private String creditorIban;

    @Column(name = "DEBTOR_NAME")
    private String debtorName;

    @Convert(converter = EncryptedStringConverter.class)
    @Column(name = "DEBTOR_IBAN")
    private String debtorIban;

    @Column(name = "BANK_TRANSACTION_CODE")
    private String bankTransactionCode;

    @Column(name = "BANK_TRANSACTION_DESCRIPTION")
    private String bankTransactionDescription;

    @Column(name = "REMITTANCE_INFORMATION", length = 1000)
    private String remittanceInformation;

    @Column(name = "NOTE")
    private String note;

    @Column(name = "CREATED_AT", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    private void onCreate() {
        if (this.uuid == null) {
            this.uuid = UUID.randomUUID();
        }
        if (this.createdAt == null) {
            this.createdAt = LocalDateTime.now();
        }
    }
}
