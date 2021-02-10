package com.ironhack.midtermbankapp.service.impl.users;

import com.ironhack.midtermbankapp.model.Users.User;
import com.ironhack.midtermbankapp.repository.users.UserRepository;
import com.ironhack.midtermbankapp.service.interfaces.users.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(long id) {
        if(!userRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        return userRepository.findById(id).get();
    }
}
