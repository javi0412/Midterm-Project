package com.ironhack.midtermbankapp.model.Accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.midtermbankapp.model.Transaction;
import com.ironhack.midtermbankapp.utils.Money;
import com.ironhack.midtermbankapp.model.Users.AccountHolder;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "balance_amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "balance_currency"))
    })
    private Money balance;

    @ManyToOne
    @JoinColumn(name = "primary_owner")
    private AccountHolder primaryOwner;

    @ManyToOne
    @JoinColumn(name = "secondary_owner")
    private AccountHolder secondaryOwner;

    private final BigDecimal penaltyFee = (new BigDecimal("40"));

    @OneToMany(mappedBy = "destinationAccount")
    private List<Transaction> receivedTransactions;
    @OneToMany(mappedBy = "origenAccount")
    private List<Transaction> sentTransactions;

    public Account() {
    }

    public Account(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
        this.secondaryOwner = secondaryOwner;
    }
    public Account(Money balance, AccountHolder primaryOwner) {
        this.balance = balance;
        this.primaryOwner = primaryOwner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Money getBalance() {
        return balance;
    }

    public void setBalance(Money balance) {
        this.balance = balance;
    }

    public AccountHolder getPrimaryOwner() {
        return primaryOwner;
    }

    public void setPrimaryOwner(AccountHolder primaryOwner) {
        this.primaryOwner = primaryOwner;
    }

    public AccountHolder getSecondaryOwner() {
        return secondaryOwner;
    }

    public void setSecondaryOwner(AccountHolder secondaryOwner) {
        this.secondaryOwner = secondaryOwner;
    }

    public BigDecimal getPenaltyFee() {
        return penaltyFee;
    }
}

