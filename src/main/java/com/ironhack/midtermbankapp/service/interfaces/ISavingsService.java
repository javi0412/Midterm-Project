package com.ironhack.midtermbankapp.service.interfaces;

import com.ironhack.midtermbankapp.dto.SavingsDTO;
import com.ironhack.midtermbankapp.dto.StatusDTO;
import com.ironhack.midtermbankapp.model.Accounts.Savings;

import java.util.List;

public interface ISavingsService {
    List<Savings> getAll();
    Savings getById(long id);
    Savings create(SavingsDTO savingsDTO);

}
