package com.ironhack.midtermbankapp.service.impl;

import com.ironhack.midtermbankapp.model.Accounts.Checking;
import com.ironhack.midtermbankapp.repository.accounts.CheckingRepository;
import com.ironhack.midtermbankapp.service.interfaces.ICheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class CheckingService implements ICheckingService {
    @Autowired
    private CheckingRepository checkingRepository;

    @Override
    public List<Checking> getAll() {
        return checkingRepository.findAll();
    }

    @Override
    public Checking getById(long id) {
        if(!checkingRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking account not found");
        }
        return checkingRepository.findById(id).get();
    }
}
