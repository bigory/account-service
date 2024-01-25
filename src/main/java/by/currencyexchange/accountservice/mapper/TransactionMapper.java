package by.currencyexchange.accountservice.mapper;

import by.currencyexchange.accountservice.dto.TransactionDto;
import by.currencyexchange.accountservice.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TransactionMapper extends BaseMapper<Transaction, TransactionDto> {
}
