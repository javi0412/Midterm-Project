package com.ironhack.midtermbankapp.service.interfaces;

import com.ironhack.midtermbankapp.dto.BalanceDTO;
import com.ironhack.midtermbankapp.dto.StatusDTO;
import com.ironhack.midtermbankapp.dto.ThirdPartyTransactionDTO;
import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.model.enums.Status;
import com.ironhack.midtermbankapp.utils.Money;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface IAccountService {

    List<Account> getAll();
    Account getById(long id);
    List<Account> getByUsername(String username);
    void updateStatus(long id, Status status);
    void updateBalance(long id, Money balance);
    void thirdPartyTransaction (Integer hashedKey, ThirdPartyTransactionDTO thirdPartyTransactionDTO);

}
