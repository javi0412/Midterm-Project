package com.ironhack.midtermbankapp.model.Users;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.utils.Address;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@DynamicUpdate
public class AccountHolder extends User {

    @NotNull
    private LocalDate dateOfBirth;

    @Embedded
    private Address primaryAddress;

    private String username;

    @OneToMany(mappedBy = "primaryOwner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Account> primaryAccounts;

    @OneToMany(mappedBy = "secondaryOwner", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Account> secondaryAccounts;

    public AccountHolder() {
    }

    public AccountHolder(@NotNull(message = "Name can not be null") String name, String username,
                         @NotNull LocalDate dateOfBirth, Address primaryAddress) {
        super(name);
        this.username=username;
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getPrimaryAddress() {
        return primaryAddress;
    }

    public void setPrimaryAddress(Address primaryAddress) {
        this.primaryAddress = primaryAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Account> getPrimaryAccounts() {
        return primaryAccounts;
    }

    public void setPrimaryAccounts(List<Account> primaryAccounts) {
        this.primaryAccounts = primaryAccounts;
    }

    public List<Account> getSecondaryAccounts() {
        return secondaryAccounts;
    }

    public void setSecondaryAccounts(List<Account> secondaryAccounts) {
        this.secondaryAccounts = secondaryAccounts;
    }
}
