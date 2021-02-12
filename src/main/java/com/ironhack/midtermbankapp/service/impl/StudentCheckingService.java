package com.ironhack.midtermbankapp.service.impl;

import com.ironhack.midtermbankapp.model.Accounts.StudentChecking;
import com.ironhack.midtermbankapp.repository.accounts.StudentCheckingRepository;
import com.ironhack.midtermbankapp.service.interfaces.IStudentCheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class StudentCheckingService implements IStudentCheckingService {

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Override
    public List<StudentChecking> getAll() {
        return studentCheckingRepository.findAll();
    }

    @Override
    public StudentChecking getById(long id) {
        if(!studentCheckingRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Student checking not found with that id");
        }
        return studentCheckingRepository.findById(id).get();
    }
}
