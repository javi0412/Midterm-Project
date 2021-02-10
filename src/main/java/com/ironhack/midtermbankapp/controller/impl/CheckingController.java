package com.ironhack.midtermbankapp.controller.impl;

import com.ironhack.midtermbankapp.controller.interfaces.ICheckingController;
import com.ironhack.midtermbankapp.dto.CheckingDTO;
import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.model.Accounts.Checking;
import com.ironhack.midtermbankapp.service.interfaces.ICheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CheckingController implements ICheckingController {

    @Autowired
    private ICheckingService checkingService;


    @Override
    @GetMapping("/checking")
    @ResponseStatus(HttpStatus.OK)
    public List<Checking> getAll() {
        return checkingService.getAll();
    }

    @Override
    @GetMapping("/checking/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Checking getById(@PathVariable long id) {
        return checkingService.getById(id);
    }

    @Override
    @PostMapping("/checking")
    @ResponseStatus(HttpStatus.CREATED)
    public Account create(@RequestBody @Valid CheckingDTO checkingDTO) {
        return checkingService.create(checkingDTO);
    }


}
