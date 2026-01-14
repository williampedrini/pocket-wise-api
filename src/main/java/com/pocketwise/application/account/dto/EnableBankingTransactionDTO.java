package com.pocketwise.application.account.dto;

import java.time.LocalDate;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;

@Builder
public record EnableBankingTransactionDTO(
        @JsonProperty("transaction_id") String transactionId,
        @JsonProperty("merchant_category_code") String merchantCategoryCode,
        @JsonProperty("transaction_amount") EnableBankingTransactionAmountDTO transactionAmount,
        @JsonProperty("creditor") EnableBankingTransactionPartyDTO creditor,
        @JsonProperty("creditor_account") EnableBankingTransactionPartyAccountDTO creditorAccount,
        @JsonProperty("creditor_agent") EnableBankingTransactionAgentDTO creditorAgent,
        @JsonProperty("debtor") EnableBankingTransactionPartyDTO debtor,
        @JsonProperty("debtor_account") EnableBankingTransactionPartyAccountDTO debtorAccount,
        @JsonProperty("debtor_agent") EnableBankingTransactionAgentDTO debtorAgent,
        @JsonProperty("bank_transaction_code") EnableBankingTransactionBankCodeDTO bankTransactionCode,
        @JsonProperty("credit_debit_indicator") String creditDebitIndicator,
        @JsonProperty("status") String status,
        @JsonProperty("booking_date") LocalDate bookingDate,
        @JsonProperty("value_date") LocalDate valueDate,
        @JsonProperty("transaction_date") LocalDate transactionDate,
        @JsonProperty("reference_number_schema") String referenceNumberSchema,
        @JsonProperty("remittance_information") List<String> remittanceInformation,
        @JsonProperty("debtor_account_additional_identification")
                EnableBankingTransactionAccountAdditionalIdentificationDTO debtorAccountAdditionalIdentification,
        @JsonProperty("creditor_account_additional_identification")
                EnableBankingTransactionAccountAdditionalIdentificationDTO creditorAccountAdditionalIdentification,
        @JsonProperty("exchange_rate") EnableBankingTransactionExchangeRateDTO exchangeRate,
        @JsonProperty("note") String note) {}
