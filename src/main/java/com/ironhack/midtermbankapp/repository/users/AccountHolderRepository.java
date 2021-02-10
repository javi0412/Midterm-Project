package com.ironhack.midtermbankapp.repository.users;

import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.model.Users.AccountHolder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountHolderRepository extends JpaRepository<AccountHolder, Long> {
}
