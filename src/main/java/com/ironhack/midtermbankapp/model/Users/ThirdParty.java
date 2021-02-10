package com.ironhack.midtermbankapp.model.Users;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;
import java.util.Objects;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@DynamicUpdate
public class ThirdParty extends User{
    @NotNull
    private Integer hashedKey;

    public ThirdParty(@NotNull(message = "Name can not be null") String name, @NotNull Integer hashedKey) {
        super(name);
        this.hashedKey = hashedKey;
    }

    public ThirdParty() {
    }


    public Integer getHashedKey() {
        return hashedKey;
    }

    public void setHashedKey(Integer hashedKey) {
        this.hashedKey = hashedKey;
    }
}
