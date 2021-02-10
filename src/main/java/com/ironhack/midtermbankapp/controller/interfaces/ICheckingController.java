package com.ironhack.midtermbankapp.controller.interfaces;

import com.ironhack.midtermbankapp.dto.CheckingDTO;
import com.ironhack.midtermbankapp.dto.StatusDTO;
import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.model.Accounts.Checking;

import java.util.List;

public interface ICheckingController {

    List<Checking> getAll();
    Checking getById(long id);
    Account create(CheckingDTO checkingDTO);
//    void updateStatus(StatusDTO statusDTO);

}
