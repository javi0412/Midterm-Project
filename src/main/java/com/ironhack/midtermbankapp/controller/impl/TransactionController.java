package com.ironhack.midtermbankapp.controller.impl;

import com.ironhack.midtermbankapp.controller.interfaces.ITransactionController;
import com.ironhack.midtermbankapp.dto.TransactionDTO;
import com.ironhack.midtermbankapp.model.Transaction;
import com.ironhack.midtermbankapp.service.interfaces.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TransactionController implements ITransactionController {

    @Autowired
    private ITransactionService transactionService;


    @Override
    @GetMapping("/admin/transaction")
    @ResponseStatus(HttpStatus.OK)
    public List<Transaction> getAll() {
        return transactionService.getAll();
    }

    @Override
    @GetMapping("/admin/transaction/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Transaction getById(@PathVariable long id) {
        return transactionService.getById(id);
    }

    @Override
    @PostMapping("/transaction")
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction create(@RequestBody @Valid TransactionDTO transactionDTO,@AuthenticationPrincipal UserDetails userDetails) {
        return transactionService.create(transactionDTO,userDetails);
    }
}
