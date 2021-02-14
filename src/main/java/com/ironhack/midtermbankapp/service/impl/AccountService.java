package com.ironhack.midtermbankapp.service.impl;

import com.ironhack.midtermbankapp.dto.StatusDTO;
import com.ironhack.midtermbankapp.dto.ThirdPartyTransactionDTO;
import com.ironhack.midtermbankapp.model.Accounts.*;
import com.ironhack.midtermbankapp.model.Users.AccountHolder;
import com.ironhack.midtermbankapp.model.Users.ThirdParty;
import com.ironhack.midtermbankapp.model.enums.Status;
import com.ironhack.midtermbankapp.model.enums.TransactionType;
import com.ironhack.midtermbankapp.repository.accounts.*;
import com.ironhack.midtermbankapp.repository.users.AccountHolderRepository;
import com.ironhack.midtermbankapp.repository.users.ThirdPartyRepository;
import com.ironhack.midtermbankapp.service.interfaces.IAccountService;
import com.ironhack.midtermbankapp.utils.InterestsAndFees;
import com.ironhack.midtermbankapp.utils.Money;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService implements IAccountService {

    @Autowired
    private SavingsRepository savingsRepository;

    @Autowired
    private CheckingRepository checkingRepository;

    @Autowired
    private StudentCheckingRepository studentCheckingRepository;

    @Autowired
    private AccountHolderRepository accountHolderRepository;

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private ThirdPartyRepository thirdPartyRepository;


    @Autowired
    private AccountRepository accountRepository;

    @Override
    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    @Override
    public Account getById(long id) {
        if(!accountRepository.existsById(id)){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
        return accountRepository.findById(id).get();
    }

    @Override
    public List<Account> getByUsername(String username) {
        Optional<AccountHolder> owner = accountHolderRepository.findByUsername(username);
        if(!owner.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Account Holder found with that username");
        }
        Optional<List<Account>> accountsOp = accountRepository.findByPrimaryOwner(owner.get());
        if(!accountsOp.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No accounts associated with that username found");
        }

        List<Account> accounts = accountsOp.get();

        if(accounts.size() >= 1 ) {
            for (Account account : accounts) {
                if (account instanceof Savings) {
                    InterestsAndFees.addInterestSavings(account.getId(), savingsRepository);
                }
                if (account instanceof CreditCard) {
                    InterestsAndFees.addInterestCreditCard(account.getId(), creditCardRepository);
                }
            }
        }

        return accounts;
    }

    @Override
    public void updateStatus(long id, Status status) {
        Optional<Account> account =accountRepository.findById(id);
        if(account.isPresent()){
            if( account.get() instanceof CreditCard){
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credit Card do not have a state");
            }
            account.get().setId(id);
            if(account.get() instanceof Savings){
                ((Savings) account.get()).setStatus(status);
                accountRepository.save(account.get());
                savingsRepository.save((Savings) account.get());
            }
            if(account.get() instanceof Checking){
                ((Checking) account.get()).setStatus(status);
                accountRepository.save(account.get());
                checkingRepository.save((Checking) account.get());
            }
            if(account.get() instanceof StudentChecking){
                ((StudentChecking) account.get()).setStatus(status);
                accountRepository.save(account.get());
                studentCheckingRepository.save((StudentChecking) account.get());
            }
        }else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }

    @Override
    public void updateBalance(long id, Money balance) {
        Optional<Account> account = accountRepository.findById(id);
        if(account.isPresent()){
            account.get().setId(id);
            account.get().setBalance(balance);
            accountRepository.save(account.get());
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account not found");
        }
    }

    @Override
    public void thirdPartyTransaction(Integer hashedKey, ThirdPartyTransactionDTO thirdPartyTransactionDTO) {
        Optional<ThirdParty> thirdPartyOp = thirdPartyRepository.findByHashedKey(hashedKey);
        Optional<Account>    accountOp    = accountRepository.findById(thirdPartyTransactionDTO.getAccountId());

        if(thirdPartyOp.isPresent() && accountOp.isPresent()){
            ThirdParty thirdParty = thirdPartyOp.get();
            Account account = accountOp.get();

            if(account instanceof Checking){
                if(((Checking) account).getSecretKey().equals(thirdPartyTransactionDTO.getSecretKey())){
                    if(thirdPartyTransactionDTO.getTransactionType().equals(TransactionType.SEND)){
                        account.setBalance(new Money(account.getBalance().increaseAmount(thirdPartyTransactionDTO.getAmount())));
                    } else{
                        account.setBalance(new Money(account.getBalance().decreaseAmount(thirdPartyTransactionDTO.getAmount())));
                        if (account.getBalance().getAmount().doubleValue() < ((Checking) account).getMinimumBalance().doubleValue()) {
                            account.setBalance(
                                    new Money(account.getBalance().decreaseAmount(account.getPenaltyFee())));
                        }
                    }
                }
                else{
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Secret Key provided does not match with the account's one");
                }
            }
            if(account instanceof StudentChecking){
                if(((StudentChecking) account).getSecretKey().equals(thirdPartyTransactionDTO.getSecretKey())){
                    if(thirdPartyTransactionDTO.getTransactionType().equals(TransactionType.SEND)){
                        account.setBalance(new Money(account.getBalance().increaseAmount(thirdPartyTransactionDTO.getAmount())));
                    } else{
                        account.setBalance(new Money(account.getBalance().decreaseAmount(thirdPartyTransactionDTO.getAmount())));
                    }
                }
                else{
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Secret Key provided does not match with the account's one");
                }
            }
            if(account instanceof Savings){
                if(((Savings) account).getSecretKey().equals(thirdPartyTransactionDTO.getSecretKey())){
                    if(thirdPartyTransactionDTO.getTransactionType().equals(TransactionType.SEND)){
                        account.setBalance(new Money(account.getBalance().increaseAmount(thirdPartyTransactionDTO.getAmount())));
                    } else{
                        account.setBalance(new Money(account.getBalance().decreaseAmount(thirdPartyTransactionDTO.getAmount())));
                    }
                }
                else{
                    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Secret Key provided does not match with the account's one");
                }
            } else{
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Credit card accounts can not be updated by third parties");
            }

            accountRepository.save(account);
        } else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account and/or third party not found");
        }

    }

}
