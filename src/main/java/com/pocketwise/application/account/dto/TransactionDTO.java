package com.pocketwise.application.account.dto;

import java.io.Serializable;
import java.time.LocalDate;

public record TransactionDTO(
        TransactionAmountDTO transactionAmount,
        TransactionPartyAccountDTO debtorAccount,
        TransactionPartyAccountDTO creditorAccount,
        String creditDebitIndicator,
        String status,
        LocalDate bookingDate,
        LocalDate valueDate,
        String description)
        implements Serializable {}
