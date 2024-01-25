package by.currencyexchange.accountservice.controller;

import by.currencyexchange.accountservice.dto.TransactionDto;
import by.currencyexchange.accountservice.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/")
public class TransactionController {

    private final TransactionService transactionService;

    @GetMapping("/transactions")
    private List<TransactionDto> getAllTransactions() {
        return transactionService.getAllTransactions();
    }


    @GetMapping("/transactions/{id}")
    private TransactionDto getTransaction(@PathVariable("id") String id) {
        return transactionService.getTransactionById(id);
    }

    @GetMapping("/transactions/account/{accountId}")
    private List<TransactionDto> getAllTransactionByAccountId(@PathVariable("accountId") Long accountId) {
        return transactionService.getAllTransactionsByAccountId(accountId);
    }

    @GetMapping("/transactions/user/{userId}")
    private List<TransactionDto> getAllTransactionByUserId(@PathVariable("userId") Long userId) {
        return transactionService.getAllTransactionsByUserId(userId);
    }

}
