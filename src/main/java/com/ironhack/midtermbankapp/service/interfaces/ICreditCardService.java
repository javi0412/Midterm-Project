package com.ironhack.midtermbankapp.service.interfaces;

import com.ironhack.midtermbankapp.dto.CreditCardDTO;
import com.ironhack.midtermbankapp.model.Accounts.CreditCard;

import java.util.List;

public interface ICreditCardService {
    List<CreditCard> getAll();
    CreditCard getById(long id);
    CreditCard create(CreditCardDTO creditCardDTO);
}
