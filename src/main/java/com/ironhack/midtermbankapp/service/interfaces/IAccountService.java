package com.ironhack.midtermbankapp.service.interfaces;

import com.ironhack.midtermbankapp.model.Accounts.Account;

import java.util.List;
import java.util.Optional;

public interface IAccountService {

    List<Account> getAll();
    Account getById(long id);
}
