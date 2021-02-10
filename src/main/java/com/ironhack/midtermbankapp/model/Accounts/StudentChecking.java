package com.ironhack.midtermbankapp.model.Accounts;

import com.ironhack.midtermbankapp.model.enums.Status;
import com.ironhack.midtermbankapp.utils.Money;
import com.ironhack.midtermbankapp.model.Users.AccountHolder;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@DynamicUpdate
public class StudentChecking extends Account{

    @NotNull(message = "Secret key can not be null")
    protected String secretKey;

    @Enumerated(value = EnumType.STRING)
    protected Status status;

    private LocalDate creationDate;  //!!!!


    public StudentChecking() {
    }

    public StudentChecking(Money balance, AccountHolder primaryOwner, AccountHolder secondaryOwner,
                           @NotNull(message = "Secret key can not be null") String secretKey, LocalDate creationDate) {
        super(balance, primaryOwner, secondaryOwner);
        this.secretKey = secretKey;
        this.status = Status.ACTIVE;
        this.creationDate = creationDate;
    }

    public StudentChecking(Money balance, AccountHolder primaryOwner,
                           @NotNull(message = "Secret key can not be null") String secretKey, LocalDate creationDate) {
        super(balance, primaryOwner);
        this.secretKey = secretKey;
        this.status = Status.ACTIVE;
        this.creationDate = creationDate;
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
}
