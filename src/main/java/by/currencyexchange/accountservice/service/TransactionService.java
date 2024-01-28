package by.currencyexchange.accountservice.service;

import by.currencyexchange.accountservice.dto.TransactionDto;
import by.currencyexchange.accountservice.entity.Transaction;
import by.currencyexchange.accountservice.exception.EntityNotFoundException;
import by.currencyexchange.accountservice.mapper.TransactionMapper;
import by.currencyexchange.accountservice.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;

    @Transactional
    public void saveTransaction(TransactionDto transactionDto) {
        transactionRepository.save(transactionMapper.toEntity(transactionDto));
    }

    public TransactionDto getTransactionById(String id) {
        return transactionMapper.toDto(transactionRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Transaction not fount by id: " + id)));
    }

    public List<TransactionDto> getAllTransactions() {
        return getListTransactionsDto(transactionRepository.findAll());
    }

    public List<TransactionDto> getAllTransactionsByAccountId(Long id) {
        return getListTransactionsDto(transactionRepository.findAllTransactionsByAccountId(id));
    }

    public List<TransactionDto> getAllTransactionsByUserId(Long id) {
        return getListTransactionsDto(transactionRepository.findAllTransactionsByUserId(id));
    }

    private List<TransactionDto> getListTransactionsDto(Iterable<Transaction> transactions) {
        return StreamSupport.stream(transactions.spliterator(), false)
                .map(transactionMapper::toDto)
                .collect(Collectors.toList());
    }

}
