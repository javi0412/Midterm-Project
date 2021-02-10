package com.ironhack.midtermbankapp.model.Users;

import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(name = "id")
@DynamicUpdate
public class Admin extends User {

    public Admin( String name, String username, String password) {
        super(name,username, password);
    }

    public Admin() {
    }


}
