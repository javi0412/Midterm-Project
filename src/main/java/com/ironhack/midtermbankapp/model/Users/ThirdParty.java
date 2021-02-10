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
    private String hashedKey;

    public ThirdParty(@NotNull(message = "Name can not be null") String name, @NotNull String hashedKey) {
        super(name);
        this.hashedKey = hashedKey;
    }

    public ThirdParty() {
    }


    @Override
    public int hashCode() {
        return Objects.hash(hashedKey);
    }
}
