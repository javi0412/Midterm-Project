package com.ironhack.midtermbankapp.dto;

import com.ironhack.midtermbankapp.model.Users.AccountHolder;
import com.ironhack.midtermbankapp.model.enums.Status;
import com.ironhack.midtermbankapp.utils.Money;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CreditCardDTO {

    private long primaryOwner;
    private long secondaryOwner;

    private LocalDate creationDate;  //!!!!

    private Money balance;

    @DecimalMax(value = "100000")
    @DecimalMin(value = "100")
    private BigDecimal creditLimit = (new BigDecimal("100"));

    @DecimalMin(value = "0.1")
    @DecimalMax(value = "0.2")
    private BigDecimal interestRate = new BigDecimal("0.2");



    public CreditCardDTO() {
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(BigDecimal creditLimit) {
        this.creditLimit = creditLimit;
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public long getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(long primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public long getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(long secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }
}
