package com.ironhack.midtermbankapp.controller.users.interfaces;

import com.ironhack.midtermbankapp.model.Users.User;

import java.util.List;

public interface IUserController {
    List<User> getAll();
    User getById(long id);
}
