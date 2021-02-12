package com.ironhack.midtermbankapp.service.impl.users;

import com.ironhack.midtermbankapp.dto.user.AccountHolderDTO;
import com.ironhack.midtermbankapp.model.Users.AccountHolder;
import com.ironhack.midtermbankapp.model.Users.Role;
import com.ironhack.midtermbankapp.repository.users.AccountHolderRepository;
import com.ironhack.midtermbankapp.repository.users.RoleRepository;
import com.ironhack.midtermbankapp.repository.users.UserRepository;
import com.ironhack.midtermbankapp.service.interfaces.users.IAccountHolderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@Service
public class AccountHolderService implements IAccountHolderService {

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<AccountHolder> getAll() {
        return accountHolderRepository.findAll();
    }

    @Override
    public AccountHolder getById(long id) {
        if (!accountHolderRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account holder not found");
        }
        return accountHolderRepository.findById(id).get();
    }

    @Override
    public AccountHolder create(AccountHolderDTO accountHolderDTO) {
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setName(accountHolderDTO.getName());
        accountHolder.setPrimaryAddress(accountHolderDTO.getPrimaryAddress());
        accountHolder.setDateOfBirth(accountHolderDTO.getDateOfBirth());
        accountHolder.setUsername(accountHolderDTO.getUsername());
        accountHolder.setPassword(accountHolderDTO.getPassword());
        userRepository.save(accountHolder);
        Role role = new Role("ACCOUNTHOLDER", accountHolder);
        roleRepository.save(role);
        return accountHolderRepository.save(accountHolder);
    }
}
