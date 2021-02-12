package com.ironhack.midtermbankapp.controller.impl;

import com.ironhack.midtermbankapp.controller.interfaces.IAccountController;
import com.ironhack.midtermbankapp.dto.BalanceDTO;
import com.ironhack.midtermbankapp.dto.StatusDTO;
import com.ironhack.midtermbankapp.dto.ThirdPartyTransactionDTO;
import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.service.interfaces.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.List;

@RestController
public class AccountController implements IAccountController {

    @Autowired
    private IAccountService accountService;

    @Override
    @GetMapping("/admin/account")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getAll() {
        return accountService.getAll();
    }

    @Override
    @GetMapping("/admin/account/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getById(@PathVariable long id) {
        return accountService.getById(id);
    }

    @Override
    @GetMapping("/account")
    @ResponseStatus(HttpStatus.OK)
    public List<Account> getByUsername(@AuthenticationPrincipal UserDetails userDetails) {
        return accountService.getByUsername(userDetails.getUsername());
    }

    @Override
    @PatchMapping("/admin/account/change-status/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateStatus(@PathVariable long id, @RequestBody @Valid StatusDTO statusDTO) {
        accountService.updateStatus(id, statusDTO.getStatus());
    }

    @Override
    @PatchMapping("/admin/account/balance/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateBalance(@PathVariable long id, @RequestBody @Valid BalanceDTO balanceDTO) {
        accountService.updateBalance(id, balanceDTO.getBalance());
    }

    @Override
    @PatchMapping("/third-party-transaction")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void thirdPartyTransaction(@RequestParam Integer hashedKey,
                                      @RequestBody @Valid ThirdPartyTransactionDTO thirdPartyTransactionDTO) {
        accountService.thirdPartyTransaction(hashedKey, thirdPartyTransactionDTO);
    }


}
