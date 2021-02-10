package com.ironhack.midtermbankapp.utils;

import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;

@Embeddable
public class Address {

    @NotNull
    private String street;

    @NotNull
    private String city;

    @NotNull
    private String country;

    @NotNull
    private String zipCode;

    public Address() {
    }

    public Address(@NotNull String street, @NotNull String city, @NotNull String country, @NotNull String zipCode) {
        this.street = street;
        this.city = city;
        this.country = country;
        this.zipCode = zipCode;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

}
