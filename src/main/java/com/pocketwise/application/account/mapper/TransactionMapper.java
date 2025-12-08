package com.pocketwise.application.account.mapper;

import java.util.Collection;
import java.util.Optional;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.pocketwise.application.account.dto.TransactionDTO;
import com.pocketwise.application.account.dto.TransactionResponseDTO;
import com.pocketwise.application.account.dto.TransactionWrapperDTO;

@Mapper(imports = {Optional.class})
public interface TransactionMapper {

    /**
     * Maps a {@link TransactionWrapperDTO} from the Enable Banking API to a {@link TransactionResponseDTO}
     * for the REST API response.
     *
     * @param value the transaction value from the Enable Banking API; must not be null.
     * @return a {@link TransactionResponseDTO} containing the transactions and pagination information.
     */
    @Mapping(target = "transactions", source = "value.transactions")
    @Mapping(
            target = "hasMore",
            expression =
                    "java(Optional.ofNullable(value.continuation()).filter(continuation -> !continuation.isEmpty()).isPresent())")
    @Mapping(target = "page", source = "value.continuation")
    TransactionResponseDTO map(TransactionWrapperDTO value);

    /**
     * Maps a collection of {@link TransactionDTO} objects to a {@link TransactionResponseDTO}
     * with custom pagination information.
     *
     * @param transactions the collection of transactions to include in the response.
     * @param hasMore indicates whether more transactions are available.
     * @param page the continuation key for the next page, or null if no more pages.
     * @return a {@link TransactionResponseDTO} containing the transactions and pagination information.
     */
    default TransactionResponseDTO map(Collection<TransactionDTO> transactions, boolean hasMore, String page) {
        return TransactionResponseDTO.builder()
                .transactions(transactions)
                .hasMore(hasMore)
                .page(page)
                .build();
    }
}
