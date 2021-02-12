package com.ironhack.midtermbankapp.controller.interfaces;

import com.ironhack.midtermbankapp.dto.SavingsDTO;
import com.ironhack.midtermbankapp.model.Accounts.Savings;
import com.ironhack.midtermbankapp.model.Accounts.StudentChecking;

import java.util.List;

public interface IStudentCheckingController {
    List<StudentChecking> getAll();
    StudentChecking getById(long id);
}
