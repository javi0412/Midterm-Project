package com.ironhack.midtermbankapp.dto.user;

import javax.validation.constraints.NotNull;
import java.util.Objects;

public class ThirdPartyDTO {

    @NotNull(message = "Name can not be null")
    private String name;

    private final Integer hashedKey = (int) Math.floor(Math.random()*(999999-100000+1)+100000);;

    public ThirdPartyDTO() {
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
