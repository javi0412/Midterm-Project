package com.ironhack.midtermbankapp.controller.interfaces;

import com.ironhack.midtermbankapp.dto.CreditCardDTO;
import com.ironhack.midtermbankapp.dto.StatusDTO;
import com.ironhack.midtermbankapp.model.Accounts.CreditCard;

import java.util.List;

public interface ICreditCardController {

    List<CreditCard> getAll();
    CreditCard getById(long id);
    CreditCard create(CreditCardDTO creditCardDTO);

}
