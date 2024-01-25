package by.currencyexchange.accountservice.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class AccountDto {

    private Long id;
    private BigDecimal balance;
    private String currencyType;
    private UserDto user;
}
