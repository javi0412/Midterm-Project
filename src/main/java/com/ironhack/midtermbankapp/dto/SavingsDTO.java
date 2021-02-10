package com.ironhack.midtermbankapp.dto;

import com.ironhack.midtermbankapp.model.Users.AccountHolder;
import com.ironhack.midtermbankapp.model.enums.Status;
import com.ironhack.midtermbankapp.utils.Money;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public class SavingsDTO {

    private long primaryOwner;
    private long secondaryOwner;

    @NotNull(message = "Secret key required")
    protected String secretKey;
    protected Status status;

    @DecimalMax(value = "0.5", message = "Maximum Interest rate must be 0.5")
    @Positive(message = "Interest rate must be positive")
    private BigDecimal interestRate = BigDecimal.valueOf(0.0025);

    @DecimalMin(value = "100", message = "Minimum balance must be above 100")
    @DecimalMax(value = "1000", message = "Minimum balance must be below 1000")
    private BigDecimal minimumBalance = BigDecimal.valueOf(1000);
    private LocalDate creationDate;  //!!!!

    private Money balance;

//    public SavingsDTO(long primaryOwner, long secondaryOwner,Status status, LocalDate creationDate, @NotNull(message = "Secret key required") String secretKey, @DecimalMax(value = "0.5", message = "Maximum Interest rate must be 0.5") @Positive(message = "Interest rate must be positive") BigDecimal interestRate, @DecimalMin(value = "100", message = "Minimum balance must be above 100") @DecimalMax(value = "1000", message = "Minimum balance must be below 1000") BigDecimal minimumBalance, Money balance) {
//        this.primaryOwner = primaryOwner;
//        this.secondaryOwner = secondaryOwner;
//        this.secretKey = secretKey;
//        this.status = status;
//        this.interestRate = interestRate;
//        this.minimumBalance = minimumBalance;
//        this.creationDate = LocalDate.now();
//        this.balance = balance;
//    }

//    public SavingsDTO(long primaryOwner, Status status, LocalDate creationDate, @NotNull(message = "Secret key required") String secretKey, @DecimalMax(value = "0.5", message = "Maximum Interest rate must be 0.5") @Positive(message = "Interest rate must be positive") BigDecimal interestRate, @DecimalMin(value = "100", message = "Minimum balance must be above 100") @DecimalMax(value = "1000", message = "Minimum balance must be below 1000") BigDecimal minimumBalance, Money balance) {
//        this.primaryOwner = primaryOwner;
//        this.secretKey = secretKey;
//        this.status = status;
//        this.interestRate = interestRate;
//        this.minimumBalance = minimumBalance;
//        this.creationDate = LocalDate.now();
//        this.balance = balance;
//    }

    public SavingsDTO() {
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

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }
}
