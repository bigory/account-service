package by.currencyexchange.accountservice.config;

import by.currencyexchange.accountservice.mapper.AccountMapper;
import by.currencyexchange.accountservice.mapper.TransactionMapper;
import by.currencyexchange.accountservice.mapper.UserMapper;
import org.mapstruct.factory.Mappers;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;


@TestConfiguration(proxyBeanMethods = false)
public class MapperConfig {

    @Bean
    public TransactionMapper transactionMapper() {
        return Mappers.getMapper(TransactionMapper.class);
    }

    @Bean
    public AccountMapper accountMapper() {
        return Mappers.getMapper(AccountMapper.class);
    }

    @Bean
    public UserMapper userMapper() {
        return Mappers.getMapper(UserMapper.class);
    }

}
