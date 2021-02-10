package com.ironhack.midtermbankapp.service.impl;

import com.ironhack.midtermbankapp.dto.TransactionDTO;
import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.model.Transaction;
import com.ironhack.midtermbankapp.repository.TransactionRepository;
import com.ironhack.midtermbankapp.repository.accounts.AccountRepository;
import com.ironhack.midtermbankapp.service.interfaces.ITransactionService;
import com.ironhack.midtermbankapp.utils.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.List;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Transaction> getAll() {
        return transactionRepository.findAll();
    }

    @Override
    public Transaction getById(long id) {
        if(!transactionRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found");
        }
        return transactionRepository.findById(id).get();
    }

//    @Override
//    public Transaction create (TransactionDTO transactionDTO) {
//        Account originAccount = accountRepository.findById(transactionDTO.getOrigenAccountId()).get();
//        List<Transaction> transactions = originAccount.getSentTransactions();
//        Transaction lastTransaction = transactions.get(transactions.size()-1);
//        long secondsBetweenTransactions = (transactionDTO.getTransactionDate().getTime() -
//                lastTransaction.getTransactionDate().getTime())/1000;
//
//
//    }
//
//
//
//    @Override
//    public Transaction create (TransactionDTO transactionDTO) {
//            Account originAccount = accountRepository.findById(transactionDTO.getOrigenAccountId()).get();
//            Account destinationAccount = accountRepository.findById(transactionDTO.getDestinationAccountId()).get();
//            Money amount = transactionDTO.getAmount();
//            String nameOwnerDestinationAccount = transactionDTO.getNameOwnerDestinationAccount();
//            String userName = originAccount.getPrimaryOwner().getUsername();
//            String password = originAccount.getPrimaryOwner().getPassword();
//            String userNameOrigin = transactionDTO.getUserNameOriginAccount();
//            String passwordOrigin = transactionDTO.getPasswordOriginAccount();
//            Boolean userBool = userName.equals(userNameOrigin) && password.equals(passwordOrigin);
//            Boolean nameBool = destinationAccount.getPrimaryOwner().getName().equals(nameOwnerDestinationAccount) ||
//                    destinationAccount.getSecondaryOwner().getName().equals(nameOwnerDestinationAccount);
//            if (userBool && nameBool) {
//                originAccount.setBalance(new Money(originAccount.getBalance().decreaseAmount(amount)));
//                destinationAccount.setBalance(new Money(destinationAccount.getBalance().increaseAmount(amount)));
//                Transaction transaction = new Transaction();
//                transaction.setDescription(transactionDTO.getDescription());
//                transaction.setAmount(transactionDTO.getAmount());
//                transaction.setTransactionDate(new Date());
//                transaction.setOrigenAccount(originAccount);
//                transaction.setDestinationAccount(destinationAccount);
//                return transactionRepository.save(transaction);
//            } else if (!userBool) {
//                throw new IllegalArgumentException("Incorrect username or password");
//            } else {
//                throw new IllegalArgumentException("The given name doest not match any account");
//            }
//    }
}
