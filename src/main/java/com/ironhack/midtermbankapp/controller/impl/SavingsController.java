package com.ironhack.midtermbankapp.controller.impl;

import com.ironhack.midtermbankapp.controller.interfaces.ISavingsController;
import com.ironhack.midtermbankapp.dto.SavingsDTO;
import com.ironhack.midtermbankapp.model.Accounts.Savings;
import com.ironhack.midtermbankapp.repository.accounts.SavingsRepository;
import com.ironhack.midtermbankapp.service.interfaces.ISavingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class SavingsController implements ISavingsController {

    @Autowired
    private ISavingsService savingsService;

    @Autowired
    private SavingsRepository savingsRepository;

    @Override
    @GetMapping("/savings")
    @ResponseStatus(HttpStatus.OK)
    public List<Savings> getAll() {
        return savingsService.getAll();
    }

    @Override
    @GetMapping("/savings/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Savings getById(@PathVariable long id) {
        return savingsService.getById(id);
    }


    @Override
    @PostMapping("/savings")
    @ResponseStatus(HttpStatus.CREATED)
    public Savings create(@RequestBody @Valid SavingsDTO savingsDTO) {
        return savingsService.create(savingsDTO);
    }



}
