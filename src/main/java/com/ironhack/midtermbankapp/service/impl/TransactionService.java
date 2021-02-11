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
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class TransactionService implements ITransactionService {

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
    public Transaction create (TransactionDTO transactionDTO) {

        Optional<Account> originAccountOp = accountRepository.findById(transactionDTO.getOrigenAccountId());
        Optional<Account> destinationAccountOp = accountRepository.findById(transactionDTO.getDestinationAccountId());
        if (originAccountOp.isPresent() && destinationAccountOp.isPresent()) {
            Account originAccount = originAccountOp.get();
            Account destinationAccount = destinationAccountOp.get();
            List<Transaction> transactions = originAccount.getSentTransactions();
            if (transactions.size() >= 1) {
                Transaction lastTransaction = transactions.get(transactions.size()-1);
                long secondsBetweenTransactions = (transactionDTO.getTransactionDate().getTime() -
                        lastTransaction.getTransactionDate().getTime())/1000;

                long last24hTransactions = transactionRepository.findTransactionsLast24h(originAccount.getId());
                long maxHistoric24hTransactions = transactionRepository.findMaxTransactions24hPeriod(originAccount.getId());
                if(secondsBetweenTransactions<=1 || last24hTransactions > 1.5* maxHistoric24hTransactions){

                    if (originAccount instanceof Checking) {
                        ((Checking) originAccount).setStatus(Status.FROZEN);
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Fraud detection activated, " +
                                "origin account frozen for security reasons ");
                    }
                    if(originAccount instanceof StudentChecking){
                        ((StudentChecking) originAccount).setStatus(Status.FROZEN);
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Fraud detection activated, " +
                                "origin account frozen for security reasons ");
                    }
                    if(originAccount instanceof Savings){
                        ((Savings) originAccount).setStatus(Status.FROZEN);
                        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Fraud detection activated, " +
                                "origin account frozen for security reasons ");
                    }
                }
                //guardo el amount que quiera transferir, el nombre del propietario de la cuenta destino, el username y password que me meten por postman,
                //y luego el username y password de la cuenta origen
                Money amount = transactionDTO.getAmount();
                String nameOwnerDestinationAccount = transactionDTO.getNameOwnerDestinationAccount();
                String userNameOrigin = transactionDTO.getUserNameOriginAccount();
                String passwordOrigin = transactionDTO.getPasswordOriginAccount();
                String userName = originAccount.getPrimaryOwner().getUsername();
                String password = originAccount.getPrimaryOwner().getPassword();
                //guardo el balance del origin account
                Money auxBalance = new Money(originAccount.getBalance().getAmount());
                //guardo en un boolean la comparación entre el username y password. En otro boolean guardo la comparación entre el name del account y el que meto por postman
                //El tercer boolean guarda la comprobación de si al realizar la transferencia, mi balance es mayor o igual a 0
                Boolean userBool = userName.equals(userNameOrigin) && password.equals(passwordOrigin);
                Boolean nameBool = destinationAccount.getPrimaryOwner().getName().equals(nameOwnerDestinationAccount) ||
                        destinationAccount.getSecondaryOwner().getName().equals(nameOwnerDestinationAccount);
                Boolean enoughBalance = auxBalance.decreaseAmount(amount).compareTo(new BigDecimal("0.00")) > -1;

                //si es correcto, hago la transferencia
                if (userBool && nameBool && enoughBalance) {
                    //si mi cuenta de origen es del tipo saving, casteo a un saving para acceder a su minimum balance.
                    if (originAccount instanceof Savings) {
                        Savings saving = (Savings) originAccount;
                        //si al realizar la transferencia me quedo por debajo del minimumbalance, aplico el panaltyfee
                        if (originAccount.getBalance().decreaseAmount(amount).compareTo(saving.getMinimumBalance()) < 0) {
                            originAccount.setBalance(new Money(originAccount.getBalance().decreaseAmount(saving.getPenaltyFee())));
                        }
                        //lo anterior pero en el caso de checking
                    } else if (originAccount instanceof Checking) {
                        Checking checking = (Checking) originAccount;
                        if (originAccount.getBalance().decreaseAmount(amount).compareTo(checking.getMinimumBalance()) < 0) {
                            originAccount.setBalance(new Money(originAccount.getBalance().decreaseAmount(checking.getPenaltyFee())));
                        }
                    } else {
                        //para cualquier otro tipo de cuenta no hay minimumbalance, simplemente hago la transferencia
                        originAccount.setBalance(new Money(originAccount.getBalance().decreaseAmount(amount)));
                    }
                    //incremento el balance d ela destination account
                    destinationAccount.setBalance(new Money(destinationAccount.getBalance().increaseAmount(amount)));
                    //creo una transferencia
                    Transaction transaction = new Transaction();
                    transaction.setDescription(transactionDTO.getDescription());
                    transaction.setAmount(transactionDTO.getAmount());
                    transaction.setTransactionDate(new Date());
                    transaction.setOrigenAccount(originAccount);
                    transaction.setDestinationAccount(destinationAccount);
                    return transactionRepository.save(transaction);
                } else if (!userBool) {
                    throw new IllegalArgumentException("Incorrect username or password");
                } else if (!nameBool) {
                    throw new IllegalArgumentException("The given name doest not match any account");
                } else {
                    throw new IllegalArgumentException("There is not enough money to make de transaction");
                }
            } else{

                //guardo el amount que quiera transferir, el nombre del propietario de la cuenta destino, el username y password que me meten por postman,
                //y luego el username y password de la cuenta origen
                Money amount = transactionDTO.getAmount();
                String nameOwnerDestinationAccount = transactionDTO.getNameOwnerDestinationAccount();
                String userNameOrigin = transactionDTO.getUserNameOriginAccount();
                String passwordOrigin = transactionDTO.getPasswordOriginAccount();
                String userName = originAccount.getPrimaryOwner().getUsername();
                String password = originAccount.getPrimaryOwner().getPassword();
                //guardo el balance del origin account
                Money auxBalance = new Money(originAccount.getBalance().getAmount());
                //guardo en un boolean la comparación entre el username y password. En otro boolean guardo la comparación entre el name del account y el que meto por postman
                //El tercer boolean guarda la comprobación de si al realizar la transferencia, mi balance es mayor o igual a 0
                Boolean userBool = userName.equals(userNameOrigin) && password.equals(passwordOrigin);
                Boolean nameBool = destinationAccount.getPrimaryOwner().getName().equals(nameOwnerDestinationAccount) ||
                        destinationAccount.getSecondaryOwner().getName().equals(nameOwnerDestinationAccount);
                Boolean enoughBalance = auxBalance.decreaseAmount(amount).compareTo(new BigDecimal("0.00")) > -1;

                //si es correcto, hago la transferencia
                if (userBool && nameBool && enoughBalance) {
                    //si mi cuenta de origen es del tipo saving, casteo a un saving para acceder a su minimum balance.
                    if (originAccount instanceof Savings) {
                        Savings saving = (Savings) originAccount;
                        //si al realizar la transferencia me quedo por debajo del minimumbalance, aplico el panaltyfee
                        if (originAccount.getBalance().decreaseAmount(amount).compareTo(saving.getMinimumBalance()) < 0) {
                            originAccount.setBalance(new Money(originAccount.getBalance().decreaseAmount(saving.getPenaltyFee())));
                        }
                        //lo anterior pero en el caso de checking
                    } else if (originAccount instanceof Checking) {
                        Checking checking = (Checking) originAccount;
                        if (originAccount.getBalance().decreaseAmount(amount).compareTo(checking.getMinimumBalance()) < 0) {
                            originAccount.setBalance(new Money(originAccount.getBalance().decreaseAmount(checking.getPenaltyFee())));
                        }
                    } else {
                        //para cualquier otro tipo de cuenta no hay minimumbalance, simplemente hago la transferencia
                        originAccount.setBalance(new Money(originAccount.getBalance().decreaseAmount(amount)));
                    }
                    //incremento el balance d ela destination account
                    destinationAccount.setBalance(new Money(destinationAccount.getBalance().increaseAmount(amount)));
                    //creo una transferencia
                    Transaction transaction = new Transaction();
                    transaction.setDescription(transactionDTO.getDescription());
                    transaction.setAmount(transactionDTO.getAmount());
                    transaction.setTransactionDate(new Date());
                    transaction.setOrigenAccount(originAccount);
                    transaction.setDestinationAccount(destinationAccount);
                    return transactionRepository.save(transaction);
                } else if (!userBool) {
                    throw new IllegalArgumentException("Incorrect username or password");
                } else if (!nameBool) {
                    throw new IllegalArgumentException("The given name doest not match any account");
                } else {
                    throw new IllegalArgumentException("There is not enough money to make de transaction");
                }


            }
        }else{
            throw new IllegalArgumentException("The given Account id doest not match any account");
        }
    }
}


