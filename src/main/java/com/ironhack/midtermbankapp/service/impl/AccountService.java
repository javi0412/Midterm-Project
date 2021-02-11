package com.ironhack.midtermbankapp.service.impl;

import com.ironhack.midtermbankapp.dto.StatusDTO;
import com.ironhack.midtermbankapp.model.Accounts.*;
import com.ironhack.midtermbankapp.model.Users.AccountHolder;
import com.ironhack.midtermbankapp.model.enums.Status;
import com.ironhack.midtermbankapp.repository.accounts.*;
import com.ironhack.midtermbankapp.repository.users.AccountHolderRepository;
import com.ironhack.midtermbankapp.service.interfaces.IAccountService;
import com.ironhack.midtermbankapp.utils.InterestsAndFees;
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
public class AccountService implements IAccountService {

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;


    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account getById(long id) {
        if(!accountRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        return accountRepository.findById(id).get();
    }

    @Override
    public List<Account> getByUsername(String username) {
        Optional<AccountHolder> owner = accountHolderRepository.findByUsername(username);
        if(!owner.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Account Holder found with that username");
        }
        Optional<List<Account>> accountsOp = accountRepository.findByPrimaryOwner(owner.get());
        if(!accountsOp.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No accounts associated with that username found");
        }
        List<Account> accounts = accountsOp.get();
        if(accounts.size() >1 ){
            for (Account account : accounts){
                if (account instanceof Savings){
                    Savings savings = (Savings) account;
                    Date dateNow = new Date();
                    int years = 0;
                    savings.getLastInterestDate().setYear(savings.getLastInterestDate().getYear()+1);
                    Date lastDate = savings.getLastInterestDate();
                    while(lastDate.before(dateNow)){
                        years++;
                        lastDate.setYear(lastDate.getYear()+1);
                    }
                    lastDate.setYear(lastDate.getYear()-1);
                    savings.setLastInterestDate(lastDate);
                    BigDecimal interest = new BigDecimal(savings.getInterestRate().doubleValue());
                    interest = interest.add(new BigDecimal(1));
                    interest = interest.pow(years);
                    savings.setBalance(new Money(savings.getBalance().getAmount().multiply(interest)));
                    savings.setLastInterestDate(lastDate);
                    savingsRepository.save(savings);
                }
                if (account instanceof CreditCard){
                    InterestsAndFees.addInterestCreditCard(account.getId(), creditCardRepository);
                }
            }
        }


        return accounts;
    }

    @Override
    public void updateStatus(long id, Status status) {
        Optional<Account> account =accountRepository.findById(id);
        if(account.isPresent()){
            if( account.get() instanceof CreditCard){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credit Card do not have a state");
            }
            account.get().setId(id);
            if(account.get() instanceof Savings){
                ((Savings) account.get()).setStatus(status);
                accountRepository.save(account.get());
                savingsRepository.save((Savings) account.get());
            }
            if(account.get() instanceof Checking){
                ((Checking) account.get()).setStatus(status);
                accountRepository.save(account.get());
                checkingRepository.save((Checking) account.get());
            }
            if(account.get() instanceof StudentChecking){
                ((StudentChecking) account.get()).setStatus(status);
                accountRepository.save(account.get());
                studentCheckingRepository.save((StudentChecking) account.get());
            }
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }

    @Override
    public void updateBalance(long id, Money balance) {
        Optional<Account> account = accountRepository.findById(id);
        if(account.isPresent()){
            account.get().setId(id);
            account.get().setBalance(balance);
            accountRepository.save(account.get());
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }

}
