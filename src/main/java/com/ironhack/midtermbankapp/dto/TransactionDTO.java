package com.ironhack.midtermbankapp.dto;

import com.ironhack.midtermbankapp.utils.Money;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

public class TransactionDTO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @NotNull
    private long origenAccountId;
    @NotNull
    private long destinationAccountId;
    @NotNull
    private String description;
    @NotNull
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "transaction_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "transaction_currency"))
    })
    private Money amount;
    private Date transactionDate = new Date();
    @NotNull
    private String nameOwnerDestinationAccount;


    public TransactionDTO(){
    }

    public TransactionDTO(@NotNull long origenAccountId, @NotNull long destinationAccountId,
                          @NotNull String description, @NotNull Money amount,
                          String nameOwnerDestinationAccount) {
        this.origenAccountId = origenAccountId;
        this.destinationAccountId = destinationAccountId;
        this.description = description;
        this.amount = amount;
        this.transactionDate = new Date();
        this.nameOwnerDestinationAccount = nameOwnerDestinationAccount;
    }

    public long getId() {
        return id;
    }

    public long getOrigenAccountId() {
        return origenAccountId;
    }
    public void setOrigenAccountId(long origenAccountId) {
        this.origenAccountId = origenAccountId;
    }
    public long getDestinationAccountId() {
        return destinationAccountId;
    }
    public void setDestinationAccountId(long destinationAccountId) {
        this.destinationAccountId = destinationAccountId;
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

    public String getNameOwnerDestinationAccount() {
        return nameOwnerDestinationAccount;
    }

    public void setNameOwnerDestinationAccount(String nameOwnerDestinationAccount) {
        this.nameOwnerDestinationAccount = nameOwnerDestinationAccount;
    }

}
