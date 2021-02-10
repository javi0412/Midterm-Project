package com.ironhack.midtermbankapp.service.impl;

import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.repository.accounts.AccountRepository;
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
}
