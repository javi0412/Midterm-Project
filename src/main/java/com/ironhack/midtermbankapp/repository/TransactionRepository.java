package com.ironhack.midtermbankapp.repository;

import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query(value = "SELECT SUM(transaction_amount) FROM transaction" +
            "WHERE (transaction_date >= now() - INTERVAL 1 DAY) AND origen_account_id = ?1", nativeQuery = true)
    Long findTransactionsLast24h(long originAccountId);

    @Query(value = "SELECT MAX(total_sum) FROM  ( " +
            "SELECT    DATE(transaction_date) as DATE, SUM(transaction_amount) total_sum " +
            "FROM      transaction " +
            "WHERE origen_account_id=?1 " +
            "AND DATE(transaction_date) != DATE(NOW()) " +
            "GROUP BY  DATE(transaction_date)) AS my_table", nativeQuery = true)
    Long findMaxTransactions24hPeriod(long originAccountId);
}
