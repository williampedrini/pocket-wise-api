package com.pocketwise.application.account.mapper;

import static java.util.Optional.ofNullable;

import java.math.BigDecimal;
import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;

import com.pocketwise.application.account.dto.EnableBankingTransactionDTO;
import com.pocketwise.application.account.dto.TransactionDTO;
import com.pocketwise.application.account.entity.Transaction;

@Mapper
public interface TransactionMapper {

    /**
     * Maps a {@link EnableBankingTransactionDTO} to a {@link Transaction} entity.
     *
     * @param value the source DTO containing transaction data.
     * @return the mapped {@link Transaction} entity.
     */
    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "uuid", ignore = true),
        @Mapping(target = "account", ignore = true),
        @Mapping(target = "createdAt", ignore = true),
        @Mapping(target = "amount", source = "value.transactionAmount.amount", qualifiedByName = "mapToDecimal"),
        @Mapping(target = "currency", source = "value.transactionAmount.currency"),
        @Mapping(target = "creditorName", source = "value.creditor.name"),
        @Mapping(target = "creditorIban", source = "value.creditorAccount.iban"),
        @Mapping(target = "debtorName", source = "value.debtor.name"),
        @Mapping(target = "debtorIban", source = "value.debtorAccount.iban"),
        @Mapping(target = "bankTransactionCode", source = "value.bankTransactionCode.code"),
        @Mapping(target = "bankTransactionDescription", source = "value.bankTransactionCode.description"),
        @Mapping(
                target = "remittanceInformation",
                source = "value.remittanceInformation",
                qualifiedByName = "mapToJoinedString")
    })
    Transaction map(EnableBankingTransactionDTO value);

    /**
     * Maps a {@link EnableBankingTransactionDTO} to a {@link TransactionDTO}.
     *
     * @param value the source EnableBanking DTO containing transaction data.
     * @return the mapped {@link TransactionDTO}.
     */
    @Mapping(target = "description", source = "value.remittanceInformation", qualifiedByName = "mapToJoinedString")
    TransactionDTO map(EnableBankingTransactionDTO value, @Context Class<TransactionDTO> targetType);

    @Named("mapToDecimal")
    default BigDecimal mapToDecimal(String value) {
        return ofNullable(value).map(BigDecimal::new).orElse(BigDecimal.ZERO);
    }

    @Named("mapToJoinedString")
    default String mapToJoinedString(List<String> values) {
        return ofNullable(values).map(list -> String.join("; ", list)).orElse("");
    }
}
