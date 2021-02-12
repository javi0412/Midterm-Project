package com.ironhack.midtermbankapp.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.model.Accounts.Checking;
import com.ironhack.midtermbankapp.model.Accounts.Savings;
import com.ironhack.midtermbankapp.model.Users.AccountHolder;
import com.ironhack.midtermbankapp.model.Users.Admin;
import com.ironhack.midtermbankapp.model.Users.Role;
import com.ironhack.midtermbankapp.model.Users.ThirdParty;
import com.ironhack.midtermbankapp.repository.TransactionRepository;
import com.ironhack.midtermbankapp.repository.accounts.AccountRepository;
import com.ironhack.midtermbankapp.repository.accounts.SavingsRepository;
import com.ironhack.midtermbankapp.repository.users.*;
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
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
class SavingsControllerTest {
    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();
        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setName("Javier");
        accountHolder.setDateOfBirth(LocalDate.of(1994 , 11, 17));
        accountHolder.setUsername("javigg");
        accountHolder.setPassword("$2a$10$hr66If9xZyBdDWrSQeyLlORqrl7lSOaAOqKwb7ipcPoO/jlE7P6YO"); //password: 123456
        accountHolder.setPrimaryAddress(new Address("Calle Radio", "Madrid", "España ...", "20019"));
        accountHolderRepository.save(accountHolder);
        AccountHolder accountHolder2 = new AccountHolder();
        accountHolder2.setName("Andres");
        accountHolder2.setDateOfBirth(LocalDate.of(1985, 10, 28));
        accountHolder2.setUsername("andres123");
        accountHolder2.setPassword("$2a$10$hr66If9xZyBdDWrSQeyLlORqrl7lSOaAOqKwb7ipcPoO");
        accountHolder2.setPrimaryAddress(new Address("Calle Ruido", "Estepona", "Spain", "56225"));
        accountHolderRepository.save(accountHolder2);
        Admin admin = new Admin();
        admin.setName("Luis");
        admin.setUsername("admin1");
        admin.setPassword("$2a$10$rZf8JHWZ1H0NXMKPFlNq1.Uj3WlOLmWygrTIov0dbKG7l4FAhVBey"); // password: 0000
        Role role = new Role();
        role.setName("ACCOUNTHOLDER");
        role.setUser(accountHolder);
        Role role2 = new Role();
        role2.setName("ACCOUNTHOLDER");
        role2.setUser(accountHolder);
        Role role3 = new Role();
        role3.setName("ADMIN");
        role3.setUser(admin);
        userRepository.saveAll(List.of(accountHolder, accountHolder2, admin));
        roleRepository.save(role2);
        roleRepository.save(role);
        roleRepository.save(role3);
        Account account = new Account();
        account.setBalance(new Money(new BigDecimal("5000")));
        account.setPrimaryOwner(accountHolder);
        accountRepository.save(account);
        Account account2 = new Account();
        account2.setBalance(new Money(new BigDecimal("6000")));
        account2.setPrimaryOwner(accountHolder);
        accountRepository.save(account2);
        Savings savings = new Savings();
        savings.setBalance(new Money(BigDecimal.valueOf(4000)));
        savings.setPrimaryOwner(accountHolder);
        savings.setSecretKey("123456");
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

    @Test
    void getAll() {
    }

    @Test
    void getById() {
    }

    @Test
    void create() {
    }
}