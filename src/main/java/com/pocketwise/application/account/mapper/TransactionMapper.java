package com.pocketwise.application.account.mapper;

import static java.util.Optional.ofNullable;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.pocketwise.application.account.dto.TransactionDTO;
import com.pocketwise.application.account.entity.Transaction;

@Mapper(componentModel = "spring")
public interface TransactionMapper {

    /**
     * Maps a {@link TransactionDTO} to a {@link Transaction} entity.
     *
     * @param value the source DTO containing transaction data.
     * @return the mapped {@link Transaction} entity.
     */
    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "uuid", ignore = true),
        @Mapping(target = "account", ignore = true),
        @Mapping(target = "createdAt", ignore = true),
        @Mapping(target = "amount", source = "value.transactionAmount.amount", qualifiedByName = "toDecimal"),
        @Mapping(target = "currency", source = "value.transactionAmount.currency"),
        @Mapping(target = "creditorName", source = "value.creditor.name"),
        @Mapping(target = "creditorIban", source = "value.creditorAccount.iban"),
        @Mapping(target = "debtorName", source = "value.debtor.name"),
        @Mapping(target = "debtorIban", source = "value.debtorAccount.iban"),
        @Mapping(target = "bankTransactionCode", source = "value.bankTransactionCode.code"),
        @Mapping(target = "bankTransactionDescription", source = "value.bankTransactionCode.description"),
        @Mapping(target = "remittanceInformation", source = "value.remittanceInformation", qualifiedByName = "joinList")
    })
    Transaction map(TransactionDTO value);

    /**
     * Maps a {@link Transaction} entity to a {@link TransactionDTO}.
     *
     * @param value the source entity.
     * @return the mapped {@link TransactionDTO}.
     */
    @Mappings({
        @Mapping(target = "transactionAmount.amount", source = "value.amount"),
        @Mapping(target = "transactionAmount.currency", source = "value.currency"),
        @Mapping(target = "creditor.name", source = "value.creditorName"),
        @Mapping(target = "creditorAccount.iban", source = "value.creditorIban"),
        @Mapping(target = "debtor.name", source = "value.debtorName"),
        @Mapping(target = "debtorAccount.iban", source = "value.debtorIban"),
        @Mapping(target = "bankTransactionCode.code", source = "value.bankTransactionCode"),
        @Mapping(target = "bankTransactionCode.description", source = "value.bankTransactionDescription"),
        @Mapping(
                target = "remittanceInformation",
                source = "value.remittanceInformation",
                qualifiedByName = "splitList"),
        @Mapping(target = "creditor.postalAddress", ignore = true),
        @Mapping(target = "debtor.postalAddress", ignore = true),
        @Mapping(target = "creditorAgent", ignore = true),
        @Mapping(target = "debtorAgent", ignore = true),
        @Mapping(target = "bankTransactionCode.subCode", ignore = true),
        @Mapping(target = "referenceNumberSchema", ignore = true),
        @Mapping(target = "debtorAccountAdditionalIdentification", ignore = true),
        @Mapping(target = "creditorAccountAdditionalIdentification", ignore = true),
        @Mapping(target = "exchangeRate", ignore = true)
    })
    TransactionDTO map(Transaction value);

    @Named("toDecimal")
    default BigDecimal toDecimal(String value) {
        return ofNullable(value).map(BigDecimal::new).orElse(BigDecimal.ZERO);
    }

    @Named("joinList")
    default String joinList(List<String> values) {
        return ofNullable(values).map(value -> String.join("; ", value)).orElse("");
    }

    @Named("splitList")
    default List<String> splitList(String value) {
        return ofNullable(value)
                .filter(value1 -> !value1.isEmpty())
                .map(value1 -> List.of(value1.split("; ")))
                .orElse(Collections.emptyList());
    }
}
