package com.ironhack.midtermbankapp.service.interfaces;

import com.ironhack.midtermbankapp.dto.CheckingDTO;
import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.model.Accounts.Checking;

import java.util.List;

public interface ICheckingService {
    List<Checking> getAll();
    Checking getById(long id);
    Account create(CheckingDTO checkingDTO);
}
