package by.currencyexchange.accountservice.repository;

import by.currencyexchange.accountservice.entity.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, String> {

    Iterable<Transaction> findAllByAccountId(Long id);

    @Query("SELECT t FROM Transaction t JOIN t.account a JOIN a.user u WHERE u.id = :userId")
    Iterable<Transaction> findAllByUserId(Long userId);

}
