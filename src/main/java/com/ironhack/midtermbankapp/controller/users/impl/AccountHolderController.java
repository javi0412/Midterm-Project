package com.ironhack.midtermbankapp.controller.users.impl;

import com.ironhack.midtermbankapp.controller.users.interfaces.IAccountHolderController;
import com.ironhack.midtermbankapp.dto.user.AccountHolderDTO;
import com.ironhack.midtermbankapp.model.Users.AccountHolder;
import com.ironhack.midtermbankapp.service.interfaces.users.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class AccountHolderController implements IAccountHolderController {

    @Autowired
    private IAccountHolderService accountHolderService;

    @Override
    @GetMapping("/admin/account-holder")
    @ResponseStatus(HttpStatus.OK)
    public List<AccountHolder> getAll() {
        return accountHolderService.getAll();
    }

    @Override
    @GetMapping("/admin/account-holder/{id}")
    @ResponseStatus(HttpStatus.OK)
    public AccountHolder getById(@PathVariable long id) {
        return accountHolderService.getById(id);
    }

    @Override
    @PostMapping("/admin/account-holder")
    @ResponseStatus(HttpStatus.CREATED)
    public AccountHolder create(@RequestBody @Valid AccountHolderDTO accountHolderDTO) {
        return accountHolderService.create(accountHolderDTO);
    }
}
