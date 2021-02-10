package com.ironhack.midtermbankapp.controller.interfaces;

import com.ironhack.midtermbankapp.dto.BalanceDTO;
import com.ironhack.midtermbankapp.dto.StatusDTO;
import com.ironhack.midtermbankapp.model.Accounts.Account;

import java.math.BigDecimal;
import java.util.List;

public interface IAccountController {

    List<Account> getAll();
    Account getById(long id);
    List<Account> getByUsername(String username);
    void updateStatus(long id, StatusDTO statusDTO);
    void updateBalance(long id, BalanceDTO balanceDTO);

}
