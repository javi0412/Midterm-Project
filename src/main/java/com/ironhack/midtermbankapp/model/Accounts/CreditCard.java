package com.ironhack.midtermbankapp.model.Accounts;

import com.ironhack.midtermbankapp.utils.Money;
import com.ironhack.midtermbankapp.model.Users.AccountHolder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@PrimaryKeyJoinColumn(name = "id")
@DynamicUpdate
public class CreditCard extends Account{


    private BigDecimal creditLimit = (new BigDecimal("100"));

    private BigDecimal interestRate = new BigDecimal("0.2");

    private LocalDate creationDate;

    private LocalDate lastAccessDate;

    public CreditCard(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                      BigDecimal creditLimit, BigDecimal interestRate) {
        super(balance, primaryOwner, secondaryOwner);
        this.creditLimit = creditLimit;
        this.interestRate = interestRate;
    }

    public CreditCard() {
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

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getLastAccessDate() {
        return lastAccessDate;
    }

    public void setLastAccessDate(LocalDate lastAccessDate) {
        this.lastAccessDate = lastAccessDate;
    }
}
