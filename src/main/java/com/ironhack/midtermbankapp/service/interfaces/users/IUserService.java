package com.ironhack.midtermbankapp.service.interfaces.users;

import com.ironhack.midtermbankapp.model.Users.User;

import java.util.List;

public interface IUserService {
    List<User> getAll();
    User getById(long id);
}
