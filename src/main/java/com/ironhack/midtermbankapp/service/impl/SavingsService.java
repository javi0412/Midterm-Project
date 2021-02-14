package com.ironhack.midtermbankapp.service.impl;

import com.ironhack.midtermbankapp.dto.SavingsDTO;
import com.ironhack.midtermbankapp.model.Accounts.Savings;
import com.ironhack.midtermbankapp.model.enums.Status;
import com.ironhack.midtermbankapp.repository.accounts.AccountRepository;
import com.ironhack.midtermbankapp.repository.accounts.SavingsRepository;
import com.ironhack.midtermbankapp.repository.users.AccountHolderRepository;
import com.ironhack.midtermbankapp.service.interfaces.ISavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class SavingsService implements ISavingsService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Override
    public List<Savings> getAll() {
        return savingsRepository.findAll();
    }

    @Override
    public Savings getById(long id) {
        if(!savingsRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Savings account not found");
        }
        return savingsRepository.findById(id).get();
    }

    @Override
    public Savings create (SavingsDTO savingsDTO){

        Savings savings = new Savings();
        savings.setInterestRate(savingsDTO.getInterestRate());
        savings.setMinimumBalance(savingsDTO.getMinimumBalance());
        savings.setSecretKey(savingsDTO.getSecretKey());
        savings.setStatus(Status.ACTIVE);
        if(savingsDTO.getBalance().getAmount().doubleValue() < savingsDTO.getMinimumBalance().doubleValue()){
            throw new IllegalArgumentException("Balance amount can not be less than minimum balance amount");
        }
        savings.setBalance(savingsDTO.getBalance());
        savings.setCreationDate(LocalDate.now());
        savings.setLastInterestDate(LocalDate.now());
        savings.setPrimaryOwner(accountHolderRepository.findById(savingsDTO.getPrimaryOwner()).get());
        if(accountHolderRepository.findById(savingsDTO.getSecondaryOwner()).isEmpty()){

        } else{
            savings.setSecondaryOwner(accountHolderRepository.findById(savingsDTO.getSecondaryOwner()).get());
        }
        accountRepository.save(savings);
        return savingsRepository.save(savings);    }



}

