package com.ironhack.midtermbankapp.model;

import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.utils.Money;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @ManyToOne
    private Account origenAccount;

    @NotNull
    @ManyToOne
    private Account destinationAccount;

    @NotNull
    private String description;

    @NotNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "transaction_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "transaction_currency"))
    })
    private Money amount;
    private Date transactionDate;

    public Transaction(@NotNull Account origenAccount, @NotNull Account destinationAccount,
                       @NotNull String description, @NotNull Money amount, Date transactionDate) {
        this.origenAccount = origenAccount;
        this.destinationAccount = destinationAccount;
        this.description = description;
        this.amount = amount;
        this.transactionDate = transactionDate;
    }

    public Transaction() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Account getOrigenAccount() {
        return origenAccount;
    }

    public void setOrigenAccount(Account origenAccount) {
        this.origenAccount = origenAccount;
    }

    public Account getDestinationAccount() {
        return destinationAccount;
    }

    public void setDestinationAccount(Account destinationAccount) {
        this.destinationAccount = destinationAccount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Money getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }
}
