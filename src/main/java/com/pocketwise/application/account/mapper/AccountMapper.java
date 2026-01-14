package com.pocketwise.application.account.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.pocketwise.application.account.dto.AccountBalanceAmountDTO;
import com.pocketwise.application.account.dto.AccountBalanceDTO;
import com.pocketwise.application.account.dto.AccountDTO;
import com.pocketwise.application.account.dto.EnableBankingAccountBalanceAmountDTO;
import com.pocketwise.application.account.dto.EnableBankingAccountBalanceDTO;
import com.pocketwise.application.account.dto.EnableBankingAccountDTO;
import com.pocketwise.application.account.entity.Account;

@Mapper
public interface AccountMapper {

    /**
     * Maps an {@link EnableBankingAccountDTO} object to an {@link Account} entity. The method performs field-level
     * transformations as defined in the mapping annotations to align the structure of the DTO with the entity.
     *
     * @param value the {@link EnableBankingAccountDTO} object containing the source data to be mapped; must not be null.
     * @return the mapped {@link Account} entity.
     */
    @Mappings({
        @Mapping(target = "session", source = "value"),
        @Mapping(target = "uuid", source = "value.uid"),
        @Mapping(target = "iban", source = "value.accountId.iban"),
    })
    Account map(EnableBankingAccountDTO value);

    /**
     * Maps an {@link Account} entity to an {@link AccountDTO}. This method transforms an {@link Account}
     * object into its corresponding {@link AccountDTO} representation by mapping specific fields as
     * defined in the mapping annotations.
     *
     * @param value the {@link Account} entity to be mapped; must not be null.
     * @return the corresponding {@link AccountDTO} with the mapped data.
     */
    @Mappings({
        @Mapping(target = "accountId.iban", source = "value.iban"),
        @Mapping(target = "uid", source = "value.uuid"),
    })
    AccountDTO map(Account value);

    /**
     * Maps an {@link EnableBankingAccountBalanceDTO} object to an {@link AccountBalanceDTO}.
     *
     * @param value the {@link EnableBankingAccountBalanceDTO} object containing the source data to be mapped; must not be null.
     * @return the mapped {@link AccountBalanceDTO}.
     */
    AccountBalanceDTO map(EnableBankingAccountBalanceDTO value);

    /**
     * Maps an {@link EnableBankingAccountBalanceAmountDTO} object to an {@link AccountBalanceAmountDTO}.
     *
     * @param value the {@link EnableBankingAccountBalanceAmountDTO} object containing the source data to be mapped; must not be null.
     * @return the mapped {@link AccountBalanceAmountDTO}.
     */
    AccountBalanceAmountDTO map(EnableBankingAccountBalanceAmountDTO value);
}
