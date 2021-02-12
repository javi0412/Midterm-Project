package com.ironhack.midtermbankapp.controller.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midtermbankapp.dto.CheckingDTO;
import com.ironhack.midtermbankapp.model.Accounts.Checking;
import com.ironhack.midtermbankapp.model.Accounts.CreditCard;
import com.ironhack.midtermbankapp.model.Accounts.StudentChecking;
import com.ironhack.midtermbankapp.model.Users.AccountHolder;
import com.ironhack.midtermbankapp.model.Users.Admin;
import com.ironhack.midtermbankapp.model.Users.Role;
import com.ironhack.midtermbankapp.repository.TransactionRepository;
import com.ironhack.midtermbankapp.repository.accounts.AccountRepository;
import com.ironhack.midtermbankapp.repository.accounts.CheckingRepository;
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
class CheckingControllerTest {

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
    private CheckingRepository checkingRepository;

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
        accountHolder2.setName("Angela");
        accountHolder2.setDateOfBirth(LocalDate.of(1997 , 11, 17));
        accountHolder2.setUsername("angeluka02");
        accountHolder2.setPassword("$2a$10$hr66If9xZyBdDWrSQeyLlORqrl7lSOaAOqKwb7ipcPoO/jlE7P6YO"); //password: 123456
        accountHolder2.setPrimaryAddress(new Address("Calle General", "Madrid", "España", "26656"));
        accountHolderRepository.save(accountHolder2);

        Admin admin = new Admin();
        admin.setName("Luis");
        admin.setUsername("admin1");
        admin.setPassword("$2a$10$rZf8JHWZ1H0NXMKPFlNq1.Uj3WlOLmWygrTIov0dbKG7l4FAhVBey"); // password: 0000
        Role role = new Role();
        role.setName("ACCOUNTHOLDER");
        role.setUser(accountHolder);
        Role role2 = new Role();
        role.setName("ACCOUNTHOLDER");
        role.setUser(accountHolder2);
        Role role3 = new Role();
        role3.setName("ADMIN");
        role3.setUser(admin);
        userRepository.saveAll(List.of(accountHolder,accountHolder2, admin));
        roleRepository.saveAll(List.of(role,role2, role3));

        Checking checking = new Checking();
        checking.setPrimaryOwner(accountHolder);
        checking.setBalance(new Money(BigDecimal.valueOf(500)));
        checking.setSecretKey("123asd");
        checkingRepository.save(checking);
    }

    @AfterEach
    void tearDown() {
        checkingRepository.deleteAll();
        accountRepository.deleteAll();
        roleRepository.deleteAll();
        accountHolderRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getAll() throws Exception {
        MvcResult result =mockMvc.perform(get("/admin/checking")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin1", "0000")))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("123asd"));
        assertTrue(result.getResponse().getContentAsString().contains("500"));
        assertTrue(result.getResponse().getContentAsString().contains("Javier"));
    }

    @Test
    void getById() throws Exception {
        List<Checking> checkingList = checkingRepository.findAll();
        MvcResult result =mockMvc.perform(get("/admin/checking/" + checkingList.get(0).getId())
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin1", "0000")))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Radio"));
        assertTrue(result.getResponse().getContentAsString().contains("500"));
        assertTrue(result.getResponse().getContentAsString().contains("1994"));
    }

    @Test
    void create_below24() throws Exception {
        AccountHolder accountHolder5 = new AccountHolder();
        accountHolder5.setName("Samuel");
        accountHolder5.setDateOfBirth(LocalDate.of(1997 , 10, 27));
        accountHolder5.setUsername("samucc");
        accountHolder5.setPassword("$2a$10$hr66If9xZyBdDWrSQeyLlORqrl7lSOaAOqKwb7ipcPoO/jlE7P6YO"); //password: 123456
        accountHolder5.setPrimaryAddress(new Address("Calle Mercedes", "Madrid", "España", "28019"));
        accountHolderRepository.save(accountHolder5);

        CheckingDTO checkingDTO = new CheckingDTO();
        checkingDTO.setPrimaryOwner(accountHolder5.getId());
        checkingDTO.setBalance(new Money(BigDecimal.valueOf(5000)));
        checkingDTO.setSecretKey("12345asd");
        StudentChecking studentChecking = new StudentChecking();

        String body = objectMapper.writeValueAsString(checkingDTO);
        MvcResult result =mockMvc.perform(
                post("/admin/checking")
                        .content(body).contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin1", "0000")))
                .andExpect(status().isCreated())
                .andReturn();
        assertFalse(result.getResponse().getContentAsString().contains("monthlyMaintenanceFee"));
        assertFalse(result.getResponse().getContentAsString().contains("minimumBalance"));
    }

    @Test
    void create_above24() throws Exception {
        AccountHolder accountHolder5 = new AccountHolder();
        accountHolder5.setName("Samuel");
        accountHolder5.setDateOfBirth(LocalDate.of(1993 , 10, 27));
        accountHolder5.setUsername("samucc");
        accountHolder5.setPassword("$2a$10$hr66If9xZyBdDWrSQeyLlORqrl7lSOaAOqKwb7ipcPoO/jlE7P6YO"); //password: 123456
        accountHolder5.setPrimaryAddress(new Address("Calle Mercedes", "Madrid", "España", "28019"));
        accountHolderRepository.save(accountHolder5);

        CheckingDTO checkingDTO = new CheckingDTO();
        checkingDTO.setPrimaryOwner(accountHolder5.getId());
        checkingDTO.setBalance(new Money(BigDecimal.valueOf(5000)));
        checkingDTO.setSecretKey("12345asd");
        StudentChecking studentChecking = new StudentChecking();

        String body = objectMapper.writeValueAsString(checkingDTO);
        MvcResult result =mockMvc.perform(
                post("/admin/checking")
                        .content(body).contentType(MediaType.APPLICATION_JSON)
                        .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin1", "0000")))
                .andExpect(status().isCreated())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("monthlyMaintenanceFee"));
        assertTrue(result.getResponse().getContentAsString().contains("minimumBalance"));
    }
}