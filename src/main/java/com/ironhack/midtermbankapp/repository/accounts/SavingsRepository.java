package com.ironhack.midtermbankapp.repository.accounts;

import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.model.Accounts.Savings;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SavingsRepository extends JpaRepository<Savings, Long> {
}
