package by.currencyexchange.accountservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class AccountDto {

    private Long id;
    private BigDecimal balance;
    private String currencyType;
    private UserDto user;

}
