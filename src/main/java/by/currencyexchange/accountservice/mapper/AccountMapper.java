package by.currencyexchange.accountservice.mapper;

import by.currencyexchange.accountservice.dto.AccountDto;
import by.currencyexchange.accountservice.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AccountMapper extends BaseMapper<Account, AccountDto> {
}
