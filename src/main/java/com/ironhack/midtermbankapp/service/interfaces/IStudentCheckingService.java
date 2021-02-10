package com.ironhack.midtermbankapp.service.interfaces;

import com.ironhack.midtermbankapp.model.Accounts.StudentChecking;

import java.util.List;

public interface IStudentCheckingService {
    List<StudentChecking> getAll();
    StudentChecking getById(long id);
}
