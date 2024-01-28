package by.currencyexchange.accountservice.service;

import by.currencyexchange.accountservice.dto.AccountDto;
import by.currencyexchange.accountservice.dto.TransactionDto;
import by.currencyexchange.accountservice.entity.CurrencyType;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@SpringBootTest
class TransferServiceTest {

    @Mock
    private TransactionService transactionService;
    @Mock
    private AccountService accountService;
    @InjectMocks
    private TransferService transferService;

    @Test
    void debitFromAccountTest() {
        LocalDateTime transactionTime = LocalDateTime.now();
        Long id = 3L;
        BigDecimal currentBalance = BigDecimal.valueOf(10000.00);
        AccountDto accountDto = AccountDto.builder()
                .id(id)
                .balance(currentBalance)
                .currencyType(CurrencyType.USD.name())
                .build();

        when(accountService.saveAccount(any(AccountDto.class))).thenReturn(accountDto);
        doNothing().when(transactionService).saveTransaction(any(TransactionDto.class));

        transferService.debitFromAccount(accountDto, BigDecimal.TEN, transactionTime);

        BigDecimal newBalance = currentBalance.subtract(BigDecimal.TEN);

        assertEquals(accountDto.getBalance(), newBalance);
        verify(accountService).saveAccount(any(AccountDto.class));
        verify(transactionService).saveTransaction(any(TransactionDto.class));
    }

}