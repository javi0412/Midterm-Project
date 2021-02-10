package com.ironhack.midtermbankapp.service.impl;

import com.ironhack.midtermbankapp.dto.CreditCardDTO;
import com.ironhack.midtermbankapp.model.Accounts.CreditCard;
import com.ironhack.midtermbankapp.repository.accounts.CreditCardRepository;
import com.ironhack.midtermbankapp.repository.users.AccountHolderRepository;
import com.ironhack.midtermbankapp.service.interfaces.ICreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
public class CreditCardService implements ICreditCardService {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Override
    public List<CreditCard> getAll() {
        return creditCardRepository.findAll();
    }

    @Override
    public CreditCard getById(long id) {
        if(!creditCardRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Credit Card account not found");
        }
        return creditCardRepository.findById(id).get();
    }

    @Override
    public CreditCard create(CreditCardDTO creditCardDTO) {
        CreditCard creditCard = new CreditCard();
        creditCard.setInterestRate(creditCardDTO.getInterestRate());
        creditCard.setCreditLimit(creditCardDTO.getCreditLimit());
        creditCard.setBalance(creditCardDTO.getBalance());
        creditCard.setPrimaryOwner(accountHolderRepository.findById(creditCardDTO.getPrimaryOwner()).get());
        creditCard.setSecondaryOwner(accountHolderRepository.findById(creditCardDTO.getSecondaryOwner()).get());
        creditCard.setCreationDate(LocalDate.now());
        return creditCardRepository.save(creditCard);
    }
}
