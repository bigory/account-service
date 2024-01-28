package by.currencyexchange.accountservice.repository;

import by.currencyexchange.accountservice.config.MapperConfig;
import by.currencyexchange.accountservice.config.TestContainerConfig;
import by.currencyexchange.accountservice.dto.TransactionDto;
import by.currencyexchange.accountservice.entity.Transaction;
import by.currencyexchange.accountservice.mapper.TransactionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;

@AutoConfigureTestDatabase(replace = Replace.NONE)
@ContextConfiguration(classes = {MapperConfig.class, TestContainerConfig.class})
@DataJpaTest
class TransactionRepositoryTest {

    @Autowired
    private TransactionRepository transactionRepository;
    @Autowired
    private TransactionMapper transactionMapper;

    @Test
    void findTransactionById() {
        String id = "36b79703-403c-4ec2-8187-6b2c50ab8ff5";

        Optional<Transaction> transaction = transactionRepository.findById(id);

        assertTrue(transaction.isPresent());
        assertNotNull(transaction);
        assertEquals(id, transaction.get().getId());
    }

    @Test
    void findAllTransactions() {
        List<TransactionDto> listTransactionsDto = getListTransactionsDto(transactionRepository.findAll());

        assertNotNull(listTransactionsDto);
        assertFalse(listTransactionsDto.isEmpty());
    }

    @Test
    void findAllTransactionsByAccountId() {
        Long accountId = 1L;

        List<TransactionDto> listTransactionsDto = getListTransactionsDto(transactionRepository.findAllTransactionsByAccountId(accountId));

        assertNotNull(listTransactionsDto);
        assertFalse(listTransactionsDto.isEmpty());
    }

    private List<TransactionDto> getListTransactionsDto(Iterable<Transaction> transactions) {
        return StreamSupport.stream(transactions.spliterator(), false)
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

}