package com.ironhack.midtermbankapp.repository;

import com.ironhack.midtermbankapp.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
