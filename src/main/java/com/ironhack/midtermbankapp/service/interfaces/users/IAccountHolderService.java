package com.ironhack.midtermbankapp.service.interfaces.users;

import com.ironhack.midtermbankapp.dto.SavingsDTO;
import com.ironhack.midtermbankapp.dto.user.AccountHolderDTO;
import com.ironhack.midtermbankapp.model.Accounts.Savings;
import com.ironhack.midtermbankapp.model.Users.AccountHolder;

import java.util.List;

public interface IAccountHolderService {
    List<AccountHolder> getAll();
    AccountHolder getById(long id);
    AccountHolder create(AccountHolderDTO accountHolderDTO);

}
