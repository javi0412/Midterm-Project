package com.ironhack.midtermbankapp;

import com.ironhack.midtermbankapp.model.Accounts.Checking;
import com.ironhack.midtermbankapp.model.Users.AccountHolder;
import com.ironhack.midtermbankapp.repository.accounts.AccountRepository;
import com.ironhack.midtermbankapp.repository.users.UserRepository;
import com.ironhack.midtermbankapp.utils.Address;
import com.ironhack.midtermbankapp.utils.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class MidtermBankappApplication {

	public static void main(String[] args) {
		SpringApplication.run(MidtermBankappApplication.class, args);
	}

}
