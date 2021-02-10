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

    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "monthly_maintenance_fee_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "monthly_maintenance_fee_currency"))
    })
    private Money monthlyMaintenanceFee = new Money(new BigDecimal("12"));

    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "minimum_balance_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "minimum_balance_currency"))
    })
    private Money  minimumBalance = new Money(new BigDecimal("250"));

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

    public Money getMonthlyMaintenanceFee() {
        return monthlyMaintenanceFee;
    }

    public Money getMinimumBalance() {
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

}
