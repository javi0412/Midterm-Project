package com.ironhack.midtermbankapp.dto.user;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class ThirdPartyDTO {

    @NotNull(message = "Name can not be null")
    private String name;

    private final Integer hashedKey = hashCode();

    public ThirdPartyDTO() {
    }


    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHashedKey() {
        return hashedKey;
    }
}
