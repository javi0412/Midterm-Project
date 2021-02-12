package com.ironhack.midtermbankapp.controller.interfaces;

import com.ironhack.midtermbankapp.dto.BalanceDTO;
import com.ironhack.midtermbankapp.dto.StatusDTO;
import com.ironhack.midtermbankapp.dto.ThirdPartyTransactionDTO;
import com.ironhack.midtermbankapp.model.Accounts.Account;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;

import java.math.BigDecimal;
import java.util.List;

public interface IAccountController {

    List<Account> getAll();
    Account getById(long id);
    List<Account> getByUsername(UserDetails userDetails);
    void updateStatus(long id, StatusDTO statusDTO);
    void updateBalance(long id, BalanceDTO balanceDTO);
    void thirdPartyTransaction (Integer hashedKey, ThirdPartyTransactionDTO thirdPartyTransactionDTO);


}
