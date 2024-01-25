package by.currencyexchange.accountservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class TransactionDto {

    private String id;
    private BigDecimal amount;
    private String transactionType;
    private LocalDateTime transactionTime;
    private AccountDto account;

}
