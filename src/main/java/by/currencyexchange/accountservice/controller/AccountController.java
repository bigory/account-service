package by.currencyexchange.accountservice.controller;

import by.currencyexchange.accountservice.dto.AccountDto;
import by.currencyexchange.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/account/{id}")
    private AccountDto getAccount(@PathVariable("id") Long id) {
        return accountService.getAccount(id);
    }

}
