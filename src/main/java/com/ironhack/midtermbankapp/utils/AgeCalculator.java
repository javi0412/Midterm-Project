package com.ironhack.midtermbankapp.utils;

import java.time.LocalDate;
import java.time.Period;

public class AgeCalculator {

    public static int calculateAge(LocalDate birthDate, LocalDate currentDate){
        if ((birthDate != null) && (currentDate != null)) {
            return Period.between(birthDate, currentDate).getYears();
        } else {
            throw new IllegalArgumentException("Birth date can not be null");
        }
    }
}
