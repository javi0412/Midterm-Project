package com.ironhack.midtermbankapp.service.impl;

import com.ironhack.midtermbankapp.dto.TransactionDTO;
import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.model.Accounts.Checking;
import com.ironhack.midtermbankapp.model.Accounts.Savings;
import com.ironhack.midtermbankapp.model.Accounts.StudentChecking;
import com.ironhack.midtermbankapp.model.Transaction;
import com.ironhack.midtermbankapp.model.enums.Status;
import com.ironhack.midtermbankapp.repository.TransactionRepository;
import com.ironhack.midtermbankapp.repository.accounts.AccountRepository;
import com.ironhack.midtermbankapp.service.interfaces.ITransactionService;
import com.ironhack.midtermbankapp.utils.FraudChecker;
import com.ironhack.midtermbankapp.utils.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {

    // Repositories dependencies injected
    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private AccountRepository accountRepository;

    private FraudChecker fraudChecker = new FraudChecker();


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


    @Override
    public Transaction create (TransactionDTO transactionDTO, UserDetails userDetails) {

        // Search the origin and destination accounts and check if they exist
        Optional<Account> originAccountOp = accountRepository.findById(transactionDTO.getOrigenAccountId());
        Optional<Account> destinationAccountOp = accountRepository.findById(transactionDTO.getDestinationAccountId());

        if (originAccountOp.isPresent() && destinationAccountOp.isPresent()) {

            Account originAccount = originAccountOp.get();
            Account destinationAccount = destinationAccountOp.get();
            // Before the transaction, we must check if any of the accounts are frozen

            //Origin account
            if(originAccount instanceof Checking ){
                if(((Checking) originAccount).getStatus().equals(Status.FROZEN)){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Origin account is frozen");
                }
            }
            if(originAccount instanceof StudentChecking){
                if(((StudentChecking) originAccount).getStatus().equals(Status.FROZEN)){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Origin account is frozen");
                }
            }
            if(originAccount instanceof Savings){
                if(((Savings) originAccount).getStatus().equals(Status.FROZEN)){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Origin account is frozen");
                }
            }

            // Destination account
            if(destinationAccount instanceof Checking){
                if(((Checking) destinationAccount).getStatus().equals(Status.FROZEN)){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Destination account is frozen");
                }

            }
            if(destinationAccount instanceof StudentChecking ){
                if(((StudentChecking) destinationAccount).getStatus().equals(Status.FROZEN)){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Destination account is frozen");
                }
            }

            if(destinationAccount instanceof Savings){
                if(((Savings) destinationAccount).getStatus().equals(Status.FROZEN)){
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Destination account is frozen");
                }
            }

            // Fraud checker, only works after 10 first transactions
            List<Transaction> transactions = originAccount.getSentTransactions();
            if (transactions.size() > 2) {
                // Check seconds between last transaction
                Transaction lastTransaction = transactions.get(transactions.size() - 1);
                long secondsBetweenTransactions = (transactionDTO.getTransactionDate().getTime() -
                        lastTransaction.getTransactionDate().getTime()) / 1000;

                // Verify if the sum of transactions of the last 24h (including this one) exceeds 150% of
                // historical maximum in 24h.
                Long last24hTransactions = transactionRepository.findTransactionsLast24h(originAccount.getId());
                Long maxHistoric24hTransactions = transactionRepository.findMaxTransactions24hPeriod(originAccount.getId());


                // If any of the conditions is achieved, origen account is frozen for security reason.


                    if (secondsBetweenTransactions < 1 || (last24hTransactions > 1.5 * maxHistoric24hTransactions)) {

                        if (originAccount instanceof Checking) {
                            ((Checking) originAccount).setStatus(Status.FROZEN);
                            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Fraud detection activated, " +
                                    "origin account frozen for security reasons ");
                        }
                        if (originAccount instanceof StudentChecking) {
                            ((StudentChecking) originAccount).setStatus(Status.FROZEN);
                            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Fraud detection activated, " +
                                    "origin account frozen for security reasons ");
                        }
                        if (originAccount instanceof Savings) {
                            ((Savings) originAccount).setStatus(Status.FROZEN);
                            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Fraud detection activated, " +
                                    "origin account frozen for security reasons ");
                        }
                }
            }
            // Now proceed the transaction itself
                Money amount = transactionDTO.getAmount();
                String nameOwnerDestinationAccount = transactionDTO.getNameOwnerDestinationAccount();

                String userName = originAccount.getPrimaryOwner().getUsername();
                String password = originAccount.getPrimaryOwner().getPassword();

                Money auxBalance = new Money(originAccount.getBalance().getAmount());

                // Check if the username and password introduced in authentication matches with the account
                Boolean userBool = userName.equals(userDetails.getUsername()) &&
                        password.equals(userDetails.getPassword());

                //Check if the name of the destination account's owner matches with the introduced in the body
                Boolean nameBool = destinationAccount.getPrimaryOwner().getName().equals(nameOwnerDestinationAccount) ||
                        destinationAccount.getSecondaryOwner().getName().equals(nameOwnerDestinationAccount);

                // Check if there's enough money to perform the transaction
                Boolean enoughBalance = auxBalance.decreaseAmount(amount).compareTo(new BigDecimal("0.0")) > -1;

                if (userBool && nameBool && enoughBalance) {

                    //Apply penalty fee depending on the account type if the balance is below minimumBalance
                    if (originAccount instanceof Savings) {

                        Savings saving = (Savings) originAccount;

                        if (originAccount.getBalance().decreaseAmount(amount).compareTo(saving.getMinimumBalance()) < 0) {
                            originAccount.setBalance(
                                    new Money(originAccount.getBalance().decreaseAmount(saving.getPenaltyFee())));
                        }

                    } else if (originAccount instanceof Checking) {

                        Checking checking = (Checking) originAccount;

                        if (originAccount.getBalance().decreaseAmount(amount).compareTo(checking.getMinimumBalance()) < 0) {
                            originAccount.setBalance(
                                    new Money(originAccount.getBalance().decreaseAmount(checking.getPenaltyFee())));
                        }

                    } else {
                        originAccount.setBalance(new Money(originAccount.getBalance().decreaseAmount(amount)));
                    }

                    destinationAccount.setBalance(new Money(destinationAccount.getBalance().increaseAmount(amount)));

                    Transaction transaction = new Transaction();
                    transaction.setDescription(transactionDTO.getDescription());
                    transaction.setAmount(transactionDTO.getAmount());
                    transaction.setTransactionDate(new Date());
                    transaction.setOrigenAccount(originAccount);
                    transaction.setDestinationAccount(destinationAccount);

                    return transactionRepository.save(transaction);

                } else if (!userBool) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Username and/or password do not match");

                } else if (!nameBool) {
                    throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Given name does not match any account");

                } else {
                    throw new IllegalArgumentException("There is not enough money to complete transaction");

                }
        } else{
            throw new ResponseStatusException (HttpStatus.NOT_FOUND, "The given account id does not match any listed account");
        }
    }
}


