package com.ironhack.midtermbankapp.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midtermbankapp.dto.CreditCardDTO;
import com.ironhack.midtermbankapp.model.Accounts.CreditCard;
import com.ironhack.midtermbankapp.model.Accounts.Savings;
import com.ironhack.midtermbankapp.model.Users.AccountHolder;
import com.ironhack.midtermbankapp.model.Users.Admin;
import com.ironhack.midtermbankapp.model.Users.Role;
import com.ironhack.midtermbankapp.repository.TransactionRepository;
import com.ironhack.midtermbankapp.repository.accounts.AccountRepository;
import com.ironhack.midtermbankapp.repository.accounts.CreditCardRepository;
import com.ironhack.midtermbankapp.repository.users.*;
import com.ironhack.midtermbankapp.utils.Address;
import com.ironhack.midtermbankapp.utils.Money;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class CreditCardControllerTest {

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
    private CreditCardRepository creditCardRepository;

    @Autowired
    private TransactionRepository transactionRepository;


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
        accountHolder.setPrimaryAddress(new Address("Calle Radio", "Madrid", "España", "20019"));
        accountHolderRepository.save(accountHolder);
        Admin admin = new Admin();
        admin.setName("Luis");
        admin.setUsername("admin1");
        admin.setPassword("$2a$10$rZf8JHWZ1H0NXMKPFlNq1.Uj3WlOLmWygrTIov0dbKG7l4FAhVBey"); // password: 0000
        Role role = new Role();
        role.setName("ACCOUNTHOLDER");
        role.setUser(accountHolder);
        Role role3 = new Role();
        role3.setName("ADMIN");
        role3.setUser(admin);
        userRepository.saveAll(List.of(accountHolder, admin));
        roleRepository.saveAll(List.of(role, role3));
        CreditCard creditCard = new CreditCard();
        creditCard.setPrimaryOwner(accountHolder);
        creditCard.setCreditLimit(BigDecimal.valueOf(500));
        creditCard.setBalance(new Money(BigDecimal.valueOf(700)));
        creditCard.setPrimaryOwner(accountHolder);
        creditCardRepository.save(creditCard);
    }

    @AfterEach
    void tearDown() {
        creditCardRepository.deleteAll();
        roleRepository.deleteAll();
        accountHolderRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getAll() throws Exception {
        MvcResult result =mockMvc.perform(get("/admin/credit-card")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin1", "0000")))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("700"));
        assertTrue(result.getResponse().getContentAsString().contains("Javier"));
        assertTrue(result.getResponse().getContentAsString().contains("500"));
    }

    @Test
    void getById() throws Exception {
        List<CreditCard> creditCardList = creditCardRepository.findAll();
        MvcResult result =mockMvc.perform(get("/admin/credit-card/" + creditCardList.get(0).getId())
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin1", "0000")))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("javigg"));
        assertTrue(result.getResponse().getContentAsString().contains("700"));
        assertTrue(result.getResponse().getContentAsString().contains("1994"));
    }

    @Test
    void create() throws Exception {

        AccountHolder accountHolder5 = new AccountHolder();
        accountHolder5.setName("Samuel");
        accountHolder5.setDateOfBirth(LocalDate.of(1991 , 10, 27));
        accountHolder5.setUsername("samucc");
        accountHolder5.setPassword("$2a$10$hr66If9xZyBdDWrSQeyLlORqrl7lSOaAOqKwb7ipcPoO/jlE7P6YO"); //password: 123456
        accountHolder5.setPrimaryAddress(new Address("Calle Mercedes", "Madrid", "España", "28019"));
        accountHolderRepository.save(accountHolder5);

        CreditCardDTO creditCardDTO = new CreditCardDTO();
        creditCardDTO.setBalance(new Money(BigDecimal.valueOf(4500)));
        creditCardDTO.setPrimaryOwner(accountHolder5.getId());

        String body = objectMapper.writeValueAsString(creditCardDTO);
        MvcResult result = mockMvc.perform(
                post("/admin/credit-card")
                        .content(body)
                        .contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin1", "0000")))
                .andExpect(status().isCreated())
                .andReturn();
    }
}