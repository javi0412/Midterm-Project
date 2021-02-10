package com.ironhack.midtermbankapp.service.interfaces;

import com.ironhack.midtermbankapp.dto.TransactionDTO;
import com.ironhack.midtermbankapp.model.Transaction;

import java.util.List;

public interface ITransactionService {

    List<Transaction> getAll();
    Transaction getById(long id);
    Transaction create(TransactionDTO transactionDTO);
}
