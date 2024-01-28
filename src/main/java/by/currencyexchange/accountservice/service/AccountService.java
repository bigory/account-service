package by.currencyexchange.accountservice.service;

import by.currencyexchange.accountservice.dto.AccountDto;
import by.currencyexchange.accountservice.exception.EntityNotFoundException;
import by.currencyexchange.accountservice.mapper.AccountMapper;
import by.currencyexchange.accountservice.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;

    public AccountDto getAccount(Long accountId) {
        return accountMapper.toDto(accountRepository.findById(accountId).orElseThrow(() ->
                new EntityNotFoundException("Invalid account is id: " + accountId)));
    }

    public AccountDto saveAccount(AccountDto accountDto) {
        return accountMapper.toDto(accountRepository.save(accountMapper.toEntity(accountDto)));
    }

}
