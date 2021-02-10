package com.ironhack.midtermbankapp.repository.users;

import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.model.Users.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
}
