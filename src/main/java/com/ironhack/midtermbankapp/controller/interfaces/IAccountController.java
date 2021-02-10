package com.ironhack.midtermbankapp.controller.interfaces;

import com.ironhack.midtermbankapp.model.Accounts.Account;

import java.util.List;

public interface IAccountController {

    List<Account> getAll();
    Account getById(long id);
}
