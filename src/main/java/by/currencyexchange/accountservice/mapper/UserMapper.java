package by.currencyexchange.accountservice.mapper;

import by.currencyexchange.accountservice.dto.UserDto;
import by.currencyexchange.accountservice.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper extends BaseMapper<User, UserDto> {
}
