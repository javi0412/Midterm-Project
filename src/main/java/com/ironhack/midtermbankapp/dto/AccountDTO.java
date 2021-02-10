package com.ironhack.midtermbankapp.dto;

import com.ironhack.midtermbankapp.model.Users.AccountHolder;
import com.ironhack.midtermbankapp.utils.Money;

import java.math.BigDecimal;

public class AccountDTO {
    private long id;
    private Money balance;
    private AccountHolder primaryOwner;
    private AccountHolder secondaryOwner;
    private final BigDecimal penaltyFee = (new BigDecimal("40"));

    public AccountDTO(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
    }

    public AccountDTO() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }
}
