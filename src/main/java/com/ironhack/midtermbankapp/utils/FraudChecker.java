package com.ironhack.midtermbankapp.utils;

import com.ironhack.midtermbankapp.dto.TransactionDTO;
import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.model.Transaction;
import com.ironhack.midtermbankapp.repository.TransactionRepository;
import com.ironhack.midtermbankapp.repository.accounts.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class FraudChecker {

    private TransactionRepository transactionRepository;

    private AccountRepository accountRepository;


    public boolean oneSecondFraudDetection(TransactionDTO transactionDTO){
        Account originAccount = accountRepository.findById(transactionDTO.getOrigenAccountId()).get();
        List<Transaction> transactions = originAccount.getSentTransactions();
        Transaction lastTransaction = transactions.get(transactions.size()-1);
        long secondsBetweenTransactions = (transactionDTO.getTransactionDate().getTime() -
                lastTransaction.getTransactionDate().getTime())/1000;
        if(secondsBetweenTransactions <=1){
            return true;
        }else{
            return false;
        }
    }

    public boolean numberOfTransactionsFraudDetection(TransactionDTO transactionDTO){
        Account originAccount = accountRepository.findById(transactionDTO.getOrigenAccountId()).get();
        Integer last24hTransactions = transactionRepository.findTransactionsLast24h(originAccount.getId());
        Integer maxHistoric24hTransactions = transactionRepository.findMaxTransactions24hPeriod(originAccount.getId());
        if(last24hTransactions > 1.5* maxHistoric24hTransactions){
            return true;
        } else{
            return false;
        }
    }


}
