package com.ironhack.midtermbankapp.controller.users.impl;

import com.ironhack.midtermbankapp.controller.users.interfaces.IUserController;
import com.ironhack.midtermbankapp.model.Users.User;
import com.ironhack.midtermbankapp.service.interfaces.users.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
public class UserController implements IUserController {

    @Autowired
    private IUserService userService;

    @Override
    @GetMapping("/user")
    @ResponseStatus(HttpStatus.OK)
    public List<User> getAll() {
        return userService.getAll();
    }

    @Override
    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User getById(@PathVariable long id) {
        return userService.getById(id);
    }
}
