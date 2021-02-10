package com.ironhack.midtermbankapp.service.impl;

import com.ironhack.midtermbankapp.dto.StatusDTO;
import com.ironhack.midtermbankapp.model.Accounts.*;
import com.ironhack.midtermbankapp.model.enums.Status;
import com.ironhack.midtermbankapp.repository.accounts.AccountRepository;
import com.ironhack.midtermbankapp.repository.accounts.CheckingRepository;
import com.ironhack.midtermbankapp.repository.accounts.SavingsRepository;
import com.ironhack.midtermbankapp.repository.accounts.StudentCheckingRepository;
import com.ironhack.midtermbankapp.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
}
