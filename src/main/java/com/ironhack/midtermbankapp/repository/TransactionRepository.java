package com.ironhack.midtermbankapp.repository;

import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "SELECT COUNT(*) FROM transaction WHERE (transaction_date >= now() - INTERVAL 1 DAY) AND origen_account_id = ?1", nativeQuery = true)
    Integer findTransactionsLast24h(long originAccountId);

    @Query(value = "SELECT MAX(count_transactions) FROM (SELECT EXTRACT(DAY FROM transaction_date) AS day," +
            "EXTRACT(MONTH FROM transaction_date) AS month," +
            "EXTRACT(YEAR FROM transaction_date) AS year," +
            "         COUNT(*) AS count_transactions" +
            "  FROM transaction" +
            "  WHERE origen_account_id = ?1" +
            " GROUP BY extract(DAY FROM transaction_date)) AS myTable", nativeQuery = true)
    Integer findMaxTransactions24hPeriod(long originAccountId);
}
