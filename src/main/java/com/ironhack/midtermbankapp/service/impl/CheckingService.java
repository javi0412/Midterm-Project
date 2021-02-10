package com.ironhack.midtermbankapp.service.impl;

import com.ironhack.midtermbankapp.dto.CheckingDTO;
import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.model.Accounts.Checking;
import com.ironhack.midtermbankapp.model.Accounts.StudentChecking;
import com.ironhack.midtermbankapp.model.enums.Status;
import com.ironhack.midtermbankapp.repository.accounts.AccountRepository;
import com.ironhack.midtermbankapp.repository.accounts.CheckingRepository;
import com.ironhack.midtermbankapp.repository.accounts.StudentCheckingRepository;
import com.ironhack.midtermbankapp.repository.users.AccountHolderRepository;
import com.ironhack.midtermbankapp.service.interfaces.ICheckingService;
import com.ironhack.midtermbankapp.utils.AgeCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.Period;
import java.util.List;

@Service
public class CheckingService implements ICheckingService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Override
    public List<Checking> getAll() {
        return checkingRepository.findAll();
    }

    @Override
    public Checking getById(long id) {
        if(!checkingRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Checking account not found");
        }
        return checkingRepository.findById(id).get();
    }

    @Override
    public Account create(CheckingDTO checkingDTO) {
        Integer age = Period.between(accountHolderRepository.findById(checkingDTO.getPrimaryOwner()).get().getDateOfBirth(),
                LocalDate.now()).getYears();
        if(age < 24){
            StudentChecking studentChecking = new StudentChecking();
            studentChecking.setBalance(checkingDTO.getBalance());
            studentChecking.setSecretKey(checkingDTO.getSecretKey());
            studentChecking.setPrimaryOwner(accountHolderRepository.findById(checkingDTO.getPrimaryOwner()).get());
            if(accountHolderRepository.findById(checkingDTO.getSecondaryOwner()).isEmpty()){

            } else{
                studentChecking.setSecondaryOwner(accountHolderRepository.findById(checkingDTO.getSecondaryOwner()).get());
            }
            studentChecking.setCreationDate(LocalDate.now());
            studentChecking.setStatus(Status.ACTIVE);
            accountRepository.save(studentChecking);
            return studentCheckingRepository.save(studentChecking);

        } else{
            Checking checking = new Checking();
            checking.setBalance(checkingDTO.getBalance());
            checking.setSecretKey(checkingDTO.getSecretKey());
            checking.setPrimaryOwner(accountHolderRepository.findById(checkingDTO.getPrimaryOwner()).get());
            if(accountHolderRepository.findById(checkingDTO.getSecondaryOwner()).isEmpty()){

            } else{
                checking.setSecondaryOwner(accountHolderRepository.findById(checkingDTO.getSecondaryOwner()).get());
            }
            checking.setCreationDate(LocalDate.now());
            checking.setStatus(Status.ACTIVE);
            accountRepository.save(checking);
            return checkingRepository.save(checking);
        }

    }
}
