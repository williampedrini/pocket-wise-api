package com.pocketwise.application.account.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record TransactionDTO(
        @JsonProperty("entry_reference") String entryReference,
        @JsonProperty("merchant_category_code") String merchantCategoryCode,
        @JsonProperty("transaction_amount") TransactionAmountDTO transactionAmount,
        @JsonProperty("creditor") TransactionPartyDTO creditor,
        @JsonProperty("creditor_account") TransactionPartyAccountDTO creditorAccount,
        @JsonProperty("creditor_agent") TransactionAgentDTO creditorAgent,
        @JsonProperty("debtor") TransactionPartyDTO debtor,
        @JsonProperty("debtor_account") TransactionPartyAccountDTO debtorAccount,
        @JsonProperty("debtor_agent") TransactionAgentDTO debtorAgent,
        @JsonProperty("bank_transaction_code") TransactionBankCodeDTO bankTransactionCode,
        @JsonProperty("credit_debit_indicator") String creditDebitIndicator,
        @JsonProperty("status") String status,
        @JsonProperty("booking_date") LocalDate bookingDate,
        @JsonProperty("value_date") LocalDate valueDate,
        @JsonProperty("transaction_date") LocalDate transactionDate,
        @JsonProperty("balance_after_transaction") TransactionAmountDTO balanceAfterTransaction,
        @JsonProperty("reference_number") String referenceNumber,
        @JsonProperty("reference_number_schema") String referenceNumberSchema,
        @JsonProperty("remittance_information") List<String> remittanceInformation,
        @JsonProperty("debtor_account_additional_identification")
                TransactionAccountAdditionalIdentificationDTO debtorAccountAdditionalIdentification,
        @JsonProperty("creditor_account_additional_identification")
                TransactionAccountAdditionalIdentificationDTO creditorAccountAdditionalIdentification,
        @JsonProperty("exchange_rate") TransactionExchangeRateDTO exchangeRate,
        @JsonProperty("note") String note,
        @JsonProperty("transaction_id") String transactionId) {}
