package com.ironhack.midtermbankapp.controller.interfaces;

import com.ironhack.midtermbankapp.dto.SavingsDTO;
import com.ironhack.midtermbankapp.model.Accounts.Savings;

import java.util.List;

public interface ISavingsController {

    List<Savings> getAll();
    Savings getById(long id);
    Savings create (SavingsDTO savingsDTO);
}
