package com.ironhack.midtermbankapp.dto;

import com.ironhack.midtermbankapp.model.enums.TransactionType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.*;
import java.math.BigDecimal;

public class ThirdPartyTransactionDTO {

    @NotNull(message = "Please, specify the amount to be transferred")
    @Positive (message = "The amount must be positive")
    private BigDecimal amount;

    @NotNull (message = "Please, specify the account id")
    private long accountId;

    @NotNull (message = "Please, specify the secretKey of the account")
    private String secretKey;

    @Enumerated(EnumType.STRING)
    @NotNull
    private TransactionType transactionType;


    public ThirdPartyTransactionDTO(){
    }

    public ThirdPartyTransactionDTO(@NotNull(message = "Please, specify the amount to be transferred")
                                    @Positive(message = "The amount must be positive") BigDecimal amount,
                                    @NotNull(message = "Please, specify the account id") long accountId,
                                    @NotNull(message = "Please, specify the secretKey of the account")
                                            String secretKey, @NotNull TransactionType transactionType) {
        this.amount = amount;
        this.accountId = accountId;
        this.secretKey = secretKey;
        this.transactionType = transactionType;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }
}

