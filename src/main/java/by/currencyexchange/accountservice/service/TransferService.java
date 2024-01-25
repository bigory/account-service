package by.currencyexchange.accountservice.service;

import by.currencyexchange.accountservice.dto.AccountDto;
import by.currencyexchange.accountservice.dto.TransactionDto;
import by.currencyexchange.accountservice.entity.Account;
import by.currencyexchange.accountservice.entity.DataTransfer;
import by.currencyexchange.accountservice.entity.TransactionType;
import by.currencyexchange.accountservice.exception.InsufficientFundsException;
import by.currencyexchange.accountservice.exception.NotAccountInCurrency;
import by.currencyexchange.accountservice.mapper.AccountMapper;
import by.currencyexchange.accountservice.repository.AccountRepository;
import by.currencyexchange.accountservice.utils.ServiceAccountUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Slf4j
@RequiredArgsConstructor
@Service
public class TransferService {

    private final AccountRepository accountRepository;
    private final AccountService accountService;
    private final TransactionService transactionService;

    private final AccountMapper accountMapper;


    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void transfer(DataTransfer dataTransfer) {
        AccountDto fromAccount = accountService.getAccount(dataTransfer.getAccountIdFrom());
        AccountDto toAccount = accountService.getAccount(dataTransfer.getAccountIdTo());
        isSameCurrencyAccount(fromAccount, toAccount, dataTransfer);
        log.info("DataTransfer: {}", dataTransfer);
        LocalDateTime transactionTime = LocalDateTime.now();
        BigDecimal amount = ServiceAccountUtil.round(dataTransfer.getAmount());
        debitFromAccount(fromAccount, amount, transactionTime);
        replenishmentAccount(toAccount, amount, transactionTime);
    }

    @Transactional(rollbackFor = InsufficientFundsException.class)
    public void debitFromAccount(AccountDto accountDto, BigDecimal amount, LocalDateTime transactionTime) {
        log.info("FROM Account id: {}", accountDto.getId());
        BigDecimal currentBalance = accountDto.getBalance();
        if (isSufficientFunds(amount, currentBalance))
            throw new InsufficientFundsException("Insufficient funds in the account " + accountDto.getId());
        BigDecimal newBalance = currentBalance.subtract(amount);
        accountDto.setBalance(newBalance);
        Account account = accountRepository.save(accountMapper.toEntity(accountDto));
        log.info("Account: {}", accountDto);
        transactionService.saveTransaction(TransactionDto.builder()
                .account(accountMapper.toDto(account))
                .amount(amount)
                .transactionType(TransactionType.DEBIT.name())
                .transactionTime(transactionTime)
                .build());
    }

    @Transactional
    public void replenishmentAccount(AccountDto accountDto, BigDecimal amount, LocalDateTime timeTransfer) {
        log.info("TO account id: {}", accountDto.getId());
        BigDecimal currentBalance = accountDto.getBalance();
        BigDecimal newBalance = currentBalance.add(ServiceAccountUtil.round(amount));
        accountDto.setBalance(newBalance);
        Account account = accountRepository.save(accountMapper.toEntity(accountDto));
        log.info("Account: {}", accountDto);
        transactionService.saveTransaction(TransactionDto.builder()
                .account(accountMapper.toDto(account))
                .amount(amount)
                .transactionType(TransactionType.REFILL.name())
                .transactionTime(timeTransfer)
                .build());
    }

    private void isSameCurrencyAccount(AccountDto fromAccountDto, AccountDto toAccountDto, DataTransfer dataTransfer) {
        if (ServiceAccountUtil.isSameCurrency(fromAccountDto.getCurrencyType(), dataTransfer.getCurrencyType().name()))
            throw new NotAccountInCurrency("No account in this currency " + dataTransfer.getCurrencyType() + " for userId: " + dataTransfer.getAccountIdFrom());
        else if (ServiceAccountUtil.isSameCurrency(toAccountDto.getCurrencyType(), dataTransfer.getCurrencyType().name()))
            throw new NotAccountInCurrency("No account in this currency " + dataTransfer.getCurrencyType() + " for userId: " + dataTransfer.getAccountIdFrom());
    }

    private boolean isSufficientFunds(BigDecimal amount, BigDecimal currentBalance) {
        return !ServiceAccountUtil.isZero(currentBalance) || !ServiceAccountUtil.equal(currentBalance, amount);
    }
}
