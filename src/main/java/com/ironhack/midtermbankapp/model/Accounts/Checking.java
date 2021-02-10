package com.ironhack.midtermbankapp.model.Accounts;

import com.ironhack.midtermbankapp.model.enums.Status;
import com.ironhack.midtermbankapp.utils.Money;
import com.ironhack.midtermbankapp.model.Users.AccountHolder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@DynamicUpdate
public class Checking extends Account {


    private String secretKey;


    private BigDecimal monthlyMaintenanceFee = (new BigDecimal("12"));


    private BigDecimal  minimumBalance = (new BigDecimal("250"));

    @Enumerated(EnumType.STRING)
    private Status status;

    private LocalDate creationDate;

    public Checking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                    @NotNull(message = "Secret key can not be null") String secretKey) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.status = Status.ACTIVE;
    }

    public Checking(Money balance, AccountHolder primaryOwner,
                    @NotNull(message = "Secret key can not be null") String secretKey) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.status = Status.ACTIVE;
    }

    public Checking() {
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public BigDecimal getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
