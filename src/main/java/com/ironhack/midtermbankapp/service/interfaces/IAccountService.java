package com.ironhack.midtermbankapp.service.interfaces;

import com.ironhack.midtermbankapp.dto.StatusDTO;
import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.model.enums.Status;

import java.util.List;
import java.util.Optional;

public interface IAccountService {

    List<Account> getAll();
    Account getById(long id);
    void updateStatus(long id, Status status);

}
