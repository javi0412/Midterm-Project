package com.ironhack.midtermbankapp.dto;

import com.ironhack.midtermbankapp.model.enums.Status;
import com.ironhack.midtermbankapp.utils.Money;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;

public class CheckingDTO {

    private long primaryOwner;

    private long secondaryOwner;

    @NotNull(message = "Secret key required")
    protected String secretKey;
    protected Status status;

    private LocalDate creationDate;

    private Money balance;

    private BigDecimal minimumBalance = BigDecimal.valueOf(250);

    private BigDecimal monthlyMaintenanceFee = (new BigDecimal("12"));

    public CheckingDTO() {
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

    public BigDecimal getMinimumBalance() {
        return minimumBalance;
    }

    public void setMinimumBalance(BigDecimal minimumBalance) {
        this.minimumBalance = minimumBalance;
    }

    public BigDecimal getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public void setMonthlyMaintenanceFee(BigDecimal monthlyMaintenanceFee) {
        this.monthlyMaintenanceFee = monthlyMaintenanceFee;
    }
}
