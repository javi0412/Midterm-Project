package com.ironhack.midtermbankapp.controller.users.interfaces;

import com.ironhack.midtermbankapp.dto.user.AccountHolderDTO;
import com.ironhack.midtermbankapp.model.Users.AccountHolder;

import java.util.List;

public interface IAccountHolderController {
    List<AccountHolder> getAll();
    AccountHolder getById(long id);
    AccountHolder create(AccountHolderDTO accountHolderDTO);
}
