package com.ironhack.midtermbankapp.model.Accounts;

import com.ironhack.midtermbankapp.model.enums.Status;
import com.ironhack.midtermbankapp.utils.Money;
import com.ironhack.midtermbankapp.model.Users.AccountHolder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@DynamicUpdate
public class Savings extends Account{

    private BigDecimal interestRate = BigDecimal.valueOf(0.0025);

    protected String secretKey;

    @Enumerated(value = EnumType.STRING)
    protected Status status;

    private BigDecimal minimumBalance= BigDecimal.valueOf(1000);

    private LocalDate creationDate;

    private LocalDate lastInterestDate;


    public Savings(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                   String secretKey) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.status = Status.ACTIVE;
        this.creationDate = LocalDate.now();
    }

    public Savings(Money balance, AccountHolder primaryOwner, String secretKey) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.status = Status.ACTIVE;
    }

    public Savings() {
        this.status = Status.ACTIVE;
        this.creationDate = LocalDate.now();
    }

    public BigDecimal getInterestRate() {
        return interestRate;
    }

    public void setInterestRate(BigDecimal interestRate) {
        this.interestRate = interestRate;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getLastInterestDate() {
        return lastInterestDate;
    }

    public void setLastInterestDate(LocalDate lastInterestDate) {
        this.lastInterestDate = lastInterestDate;
    }
}
