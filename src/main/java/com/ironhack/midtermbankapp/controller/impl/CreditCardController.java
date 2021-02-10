package com.ironhack.midtermbankapp.controller.impl;

import com.ironhack.midtermbankapp.controller.interfaces.ICreditCardController;
import com.ironhack.midtermbankapp.dto.CreditCardDTO;
import com.ironhack.midtermbankapp.model.Accounts.CreditCard;
import com.ironhack.midtermbankapp.service.interfaces.ICreditCardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class CreditCardController implements ICreditCardController {
    @Autowired
    private ICreditCardService creditCardService;

    @Override
    @GetMapping("credit-card")
    @ResponseStatus(HttpStatus.OK)
    public List<CreditCard> getAll() {
        return creditCardService.getAll();
    }

    @Override
    @GetMapping("/credit-card/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CreditCard getById(@PathVariable long id) {
        return creditCardService.getById(id);
    }

    @Override
    @PostMapping("/credit-card")
    @ResponseStatus(HttpStatus.CREATED)
    public CreditCard create(@RequestBody @Valid CreditCardDTO creditCardDTO) {
        return creditCardService.create(creditCardDTO);
    }
}
