package com.ironhack.midtermbankapp.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.model.Users.User;
import com.ironhack.midtermbankapp.utils.Address;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public class AccountHolderDTO {
    @NotNull(message = "Name can not be empty")
    private String name;

    @NotNull(message = "Date of birth can not be empty")
    private LocalDate dateOfBirth;

    @Embedded
    private Address primaryAddress ;

    @NotNull(message = "Username can not be empty")
    private String username;

    @NotNull(message = "Password can not be empty")
    private String password;


    public AccountHolderDTO() {
    }

    public AccountHolderDTO(@NotNull(message = "Name can not be empty") String name,
                            @NotNull(message = "Date of birth can not be empty") LocalDate dateOfBirth,
                            Address primaryAddress, @NotNull(message = "Username can not be empty") String username,
                            @NotNull(message = "Password can not be empty") String password) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.primaryAddress = primaryAddress;
        this.username = username;
        this.password = password;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}