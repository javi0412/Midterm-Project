package com.ironhack.midtermbankapp.controller.impl;

import com.ironhack.midtermbankapp.controller.interfaces.IStudentCheckingController;
import com.ironhack.midtermbankapp.model.Accounts.StudentChecking;
import com.ironhack.midtermbankapp.service.interfaces.IStudentCheckingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentCheckingController implements IStudentCheckingController {

    @Autowired
    private IStudentCheckingService studentCheckingService;

    @Override
    @GetMapping("/admin/student-checking")
    @ResponseStatus(HttpStatus.OK)
    public List<StudentChecking> getAll() {
        return studentCheckingService.getAll();
    }

    @Override
    @GetMapping("/admin/student-checking/{id}")
    @ResponseStatus(HttpStatus.OK)
    public StudentChecking getById(@PathVariable long id) {
        return studentCheckingService.getById(id);
    }
}
