package com.ironhack.midtermbankapp.controller.interfaces;

import com.ironhack.midtermbankapp.dto.TransactionDTO;
import com.ironhack.midtermbankapp.model.Transaction;

import java.util.List;

public interface ITransactionController {

    List<Transaction> getAll();
    Transaction getById(long id);
//    Transaction create(TransactionDTO transactionDTO);
}
