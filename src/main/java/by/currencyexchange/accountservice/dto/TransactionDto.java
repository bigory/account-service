package by.currencyexchange.accountservice.dto;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Builder
@Data
public class TransactionDto {

    private String id;
    private BigDecimal amount;
    private String transactionType;
    private LocalDateTime transactionTime;
    private AccountDto account;

}
