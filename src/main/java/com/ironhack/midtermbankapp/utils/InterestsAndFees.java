package com.ironhack.midtermbankapp.utils;

import com.ironhack.midtermbankapp.model.Accounts.CreditCard;
import com.ironhack.midtermbankapp.model.Accounts.Savings;
import com.ironhack.midtermbankapp.repository.accounts.CreditCardRepository;
import com.ironhack.midtermbankapp.repository.accounts.SavingsRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

public class InterestsAndFees {

    @Autowired
    private CreditCardRepository creditCardRepository;

    @Autowired
    private SavingsRepository savingsRepository;

    public static void addInterestCreditCard(Long id, CreditCardRepository creditCardRepository) {
        CreditCard creditCard = creditCardRepository.findById(id).get();

        Integer monthsSinceCreation = Period.between(LocalDate.now(), creditCard.getCreationDate()).getMonths();
        Integer monthsSinceLastAccess = Period.between(LocalDate.now(), creditCard.getLastAccessDate()).getMonths();

        BigDecimal interestPerMonth = (creditCard.getInterestRate().divide(new BigDecimal(12.0), 2, RoundingMode.HALF_UP));
        Integer numberMonths = monthsSinceCreation - monthsSinceLastAccess;
        BigDecimal totalInterest = (interestPerMonth.multiply(new BigDecimal(numberMonths))).add(new BigDecimal(1));

        if (monthsSinceCreation > monthsSinceLastAccess) {
            creditCard.setBalance(new Money(creditCard.getBalance().getAmount().multiply(totalInterest)));
            creditCard.setLastAccessDate(LocalDate.now());
            creditCardRepository.save(creditCard);
        }
    }

    public static void addInterestSavings(Long id, SavingsRepository savingsRepository) {
        Savings savings = savingsRepository.findById(id).get();

        Integer yearsSinceCreation = Period.between(LocalDate.now(), savings.getCreationDate()).getYears();
        Integer yearsSinceLastAccess = Period.between(LocalDate.now(), savings.getLastInterestDate()).getYears();

        BigDecimal interestPerYear = savings.getInterestRate();
        Integer numberYears = yearsSinceCreation - yearsSinceLastAccess;

        BigDecimal totalInterest = (interestPerYear.multiply(new BigDecimal(numberYears))).add(new BigDecimal(1));

        if (yearsSinceCreation > yearsSinceLastAccess) {
            savings.setBalance(new Money(savings.getBalance().getAmount().multiply(totalInterest)));
            savings.setLastInterestDate(LocalDate.now());
            savingsRepository.save(savings);
        }
    }

}
