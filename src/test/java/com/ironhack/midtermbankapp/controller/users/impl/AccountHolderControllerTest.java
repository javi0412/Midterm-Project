package com.ironhack.midtermbankapp.controller.users.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.midtermbankapp.dto.user.AccountHolderDTO;
import com.ironhack.midtermbankapp.model.Users.AccountHolder;
import com.ironhack.midtermbankapp.model.Users.Admin;
import com.ironhack.midtermbankapp.model.Users.Role;
import com.ironhack.midtermbankapp.repository.TransactionRepository;
import com.ironhack.midtermbankapp.repository.accounts.AccountRepository;
import com.ironhack.midtermbankapp.repository.accounts.CheckingRepository;
import com.ironhack.midtermbankapp.repository.users.AccountHolderRepository;
import com.ironhack.midtermbankapp.repository.users.AdminRepository;
import com.ironhack.midtermbankapp.repository.users.RoleRepository;
import com.ironhack.midtermbankapp.repository.users.UserRepository;
import com.ironhack.midtermbankapp.utils.Address;
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

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class AccountHolderControllerTest {

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

    private MockMvc mockMvc;
    private ObjectMapper objectMapper = new ObjectMapper();


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).apply(springSecurity()).build();

        AccountHolder accountHolder = new AccountHolder();
        accountHolder.setName("Javier");
        accountHolder.setDateOfBirth(LocalDate.of(1994, 11, 17));
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

        Role role3 = new Role();
        role3.setName("ADMIN");
        role3.setUser(admin);

        userRepository.saveAll(List.of(accountHolder, admin));
        roleRepository.saveAll(List.of(role, role3));
    }

    @AfterEach
    void tearDown() {
        roleRepository.deleteAll();
        accountHolderRepository.deleteAll();
        userRepository.deleteAll();
    }

    @Test
    void getAll() throws Exception {
        MvcResult result = mockMvc.perform(get("/admin/account-holder")
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin1", "0000")))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("javigg"));
        assertTrue(result.getResponse().getContentAsString().contains("Javier"));
        assertTrue(result.getResponse().getContentAsString().contains("angeluka02"));
    }

    @Test
    void getById() throws Exception {
        List<AccountHolder> accountHolders = accountHolderRepository.findAll();
        MvcResult result = mockMvc.perform(get("/admin/account-holder/" + accountHolders.get(0).getId())
                .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin1", "0000")))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("javigg"));
        assertTrue(result.getResponse().getContentAsString().contains("Madrid"));
        assertTrue(result.getResponse().getContentAsString().contains("1994"));

    }

    @Test
    void create() throws Exception {

        String body = " {\n" +
                "        \"name\": \"Javichu Garcia\",\n" +
                "        \"dateOfBirth\": \"1994-11-17\",\n" +
                "        \"primaryAddress\": {\n" +
                "            \"street\": \"Calle Radio\",\n" +
                "            \"city\": \"Madrid\",\n" +
                "            \"country\": \"Spain\",\n" +
                "            \"zipCode\": \"28019\"\n" +
                "        },\n" +
                "        \"username\": \"javi94\", \n" +
                "        \"password\": \"$2a$10$BE3p4yAl6JDeYhRuXmPUsOwFPn/2ZA8U7loYUoQ/.bk.OOgH10njW\"\n" +
                "    }";

        MvcResult result = mockMvc.perform(
                    post("/admin/account-holder")
                            .content(body)
                            .contentType(MediaType.APPLICATION_JSON)
                            .with(SecurityMockMvcRequestPostProcessors.httpBasic("admin1", "0000")))
                    .andExpect(status().isCreated())
                    .andReturn();
            assertTrue(result.getResponse().getContentAsString().contains("javi94"));

    }
}