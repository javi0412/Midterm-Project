package com.ironhack.midtermbankapp.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midtermbankapp.dto.TransactionDTO;
import com.ironhack.midtermbankapp.model.Accounts.Account;
import com.ironhack.midtermbankapp.model.Accounts.Savings;
import com.ironhack.midtermbankapp.model.Transaction;
import com.ironhack.midtermbankapp.model.Users.AccountHolder;
import com.ironhack.midtermbankapp.model.Users.Admin;
import com.ironhack.midtermbankapp.model.Users.Role;
import com.ironhack.midtermbankapp.model.enums.Status;
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
import org.springframework.http.MediaType;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class TransactionControllerTest {

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
        roleRepository.saveAll(List.of(role,role2,role3));

        Savings savings = new Savings();
        savings.setBalance(new Money(BigDecimal.valueOf(4000)));
        savings.setPrimaryOwner(accountHolder);
        savings.setSecretKey("123456");
        savingsRepository.save(savings);

        Savings savings2 = new Savings();
        savings2.setBalance(new Money(BigDecimal.valueOf(5000)));
        savings2.setPrimaryOwner(accountHolder2);
        savings2.setSecretKey("66565");
        savingsRepository.save(savings2);

        Transaction transaction = new Transaction();
        transaction.setOrigenAccount(savings);
        transaction.setDestinationAccount(savings2);
        transaction.setAmount(new Money(BigDecimal.valueOf(300)));
        transaction.setDescription("Transferencia");
        transactionRepository.save(transaction);
    }

    @AfterEach
    void tearDown() {
        transactionRepository.deleteAll();
        savingsRepository.deleteAll();
        accountRepository.deleteAll();
        roleRepository.deleteAll();
        accountHolderRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getAll() throws Exception {
        MvcResult result =mockMvc.perform(get("/admin/transaction")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin1", "0000")))
                .andExpect(status().isOk())
                .andReturn();

        assertTrue(result.getResponse().getContentAsString().contains("Transferencia"));
        assertTrue(result.getResponse().getContentAsString().contains("300"));
        assertTrue(result.getResponse().getContentAsString().contains("Javier"));
    }

    @Test
    void getById() throws Exception {
        List<Transaction> transactions = transactionRepository.findAll();

        MvcResult result =mockMvc.perform(get("/admin/transaction/" + transactions.get(0).getId())
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin1", "0000")))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("javigg"));
        assertTrue(result.getResponse().getContentAsString().contains("1994"));
        assertTrue(result.getResponse().getContentAsString().contains("Transferencia"));
    }

    @Test
    void create() throws Exception {
        TransactionDTO transactionDTO = new TransactionDTO();
        List<Savings> savingss = savingsRepository.findAll();
        Savings originAccount = savingss.get(0);
        Savings destinationAccount = savingss.get(1);
        transactionDTO.setOrigenAccountId(originAccount.getId());
        transactionDTO.setDestinationAccountId(destinationAccount.getId());
        transactionDTO.setAmount(new Money(BigDecimal.valueOf(500)));
        transactionDTO.setDescription("Blablabla");
        transactionDTO.setNameOwnerDestinationAccount(destinationAccount.getPrimaryOwner().getName());

        String body = objectMapper.writeValueAsString(transactionDTO);

        MvcResult result =mockMvc.perform(
                post("/transaction")
                        .content(body).contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("javigg", "123456")))
                .andExpect(status().isCreated())
                .andReturn();
    }

    @Test
    void create_fraudChecker() throws Exception {
        List<Savings> savingss = savingsRepository.findAll();
        Savings originAccount = savingss.get(0);
        Savings destinationAccount = savingss.get(1);

        TransactionDTO transactionDTO = new TransactionDTO();

        transactionDTO.setOrigenAccountId(originAccount.getId());
        transactionDTO.setDestinationAccountId(destinationAccount.getId());
        transactionDTO.setAmount(new Money(BigDecimal.valueOf(500)));
        transactionDTO.setDescription("Blablabla");
        transactionDTO.setNameOwnerDestinationAccount(destinationAccount.getPrimaryOwner().getName());
        transactionDTO.setTransactionDate(new Date());

        String body = objectMapper.writeValueAsString(transactionDTO);

        MvcResult result =mockMvc.perform(
                post("/transaction")
                        .content(body).contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("javigg", "123456")))
                .andExpect(status().isCreated())
                .andReturn();


        TransactionDTO transactionDTO2 = new TransactionDTO();

        transactionDTO2.setOrigenAccountId(originAccount.getId());
        transactionDTO2.setDestinationAccountId(destinationAccount.getId());
        transactionDTO2.setAmount(new Money(BigDecimal.valueOf(500)));
        transactionDTO2.setDescription("Blablabla");
        transactionDTO2.setNameOwnerDestinationAccount(destinationAccount.getPrimaryOwner().getName());
        transactionDTO2.setTransactionDate(new Date(transactionDTO.getTransactionDate().getTime()+500));

        String body2 = objectMapper.writeValueAsString(transactionDTO2);

        MvcResult result2 =mockMvc.perform(
                post("/transaction")
                        .content(body2).contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("javigg", "123456")))
                .andExpect(status().isCreated())
                .andReturn();

        TransactionDTO transactionDTO3 = new TransactionDTO();

        transactionDTO3.setOrigenAccountId(originAccount.getId());
        transactionDTO3.setDestinationAccountId(destinationAccount.getId());
        transactionDTO3.setAmount(new Money(BigDecimal.valueOf(500)));
        transactionDTO3.setDescription("Blablabla");
        transactionDTO3.setNameOwnerDestinationAccount(destinationAccount.getPrimaryOwner().getName());
        transactionDTO3.setTransactionDate(new Date(transactionDTO2.getTransactionDate().getTime()+500));

        String body3 = objectMapper.writeValueAsString(transactionDTO3);

        MvcResult result3 =mockMvc.perform(
                post("/transaction")
                        .content(body3).contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("javigg", "123456")))
                .andExpect(status().isForbidden())
                .andReturn();
    }
}