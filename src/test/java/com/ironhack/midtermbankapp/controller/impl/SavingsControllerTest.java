package com.ironhack.midtermbankapp.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.model.Accounts.Checking;
import com.ironhack.midtermbankapp.model.Accounts.Savings;
import com.ironhack.midtermbankapp.model.Users.AccountHolder;
import com.ironhack.midtermbankapp.repository.accounts.AccountRepository;
import com.ironhack.midtermbankapp.repository.accounts.SavingsRepository;
import com.ironhack.midtermbankapp.repository.users.UserRepository;
import com.ironhack.midtermbankapp.utils.Address;
import com.ironhack.midtermbankapp.utils.Money;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
class SavingsControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private UserRepository userRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        Address address = new Address("Calle Radio", "Madrid", "Spain", "28012");
        AccountHolder accountHolder = new AccountHolder("Javier Garcia", "javiicc",
                LocalDate.of(1994, 11, 17),address );
        Savings savings = new Savings(new Money(BigDecimal.valueOf(1000)), accountHolder,"123456a");
        savings.setInterestRate(BigDecimal.valueOf(0.2));
        savings.setMinimumBalance(BigDecimal.valueOf(500));
        userRepository.save(accountHolder);
        savingsRepository.save(savings);
    }

    @AfterEach
    void tearDown() {
        savingsRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getAll_NoParams_returnAll() throws Exception {
        MvcResult result =mockMvc.perform(get("/savings")).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("123456a"));
        assertTrue(result.getResponse().getContentAsString().contains("Garcia"));
        assertTrue(result.getResponse().getContentAsString().contains("Madrid"));
    }

    @Test
    void getById_validId_returnSavings() throws Exception {
        List<Savings> savings = savingsRepository.findAll();
        MvcResult result =mockMvc.perform(get("/savings/" + savings.get(0).getId())).andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("123456a"));
        assertTrue(result.getResponse().getContentAsString().contains("Javier"));
        assertTrue(result.getResponse().getContentAsString().contains("28012"));
    }
}