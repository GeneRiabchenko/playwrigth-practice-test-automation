package com.playwright.toolshop.utils;

import net.datafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.playwright.toolshop.testresources.Resources.USER_PASSWORD;

public class UserBuilder {
    private String first_name;
    private String last_name;
    private Address address;
    private String phone;
    private String dob;
    private String password;
    private String email;

    private UserBuilder() {
        Faker fake = new Faker();
        this.first_name = fake.name().firstName();
        this.last_name = fake.name().lastName();
        this.address = Address.randomAddress();
        this.phone = fake.number().digits(10);
        this.dob = randomDob(fake);
        this.password = USER_PASSWORD;
        this.email = fake.internet().emailAddress();
    }

    private UserBuilder(User user) {
        this.first_name = user.first_name();
        this.last_name = user.last_name();
        this.address = user.address();
        this.phone = user.phone();
        this.dob = user.dob();
        this.password = user.password();
        this.email = user.email();
    }

    public static UserBuilder aUser() {
        return new UserBuilder();
    }

    public static UserBuilder from(User user) {
        return new UserBuilder(user);
    }

    public UserBuilder withFirstName(String first_name) {
        this.first_name = first_name;
        return this;
    }

    public UserBuilder withLastName(String last_name) {
        this.last_name = last_name;
        return this;
    }

    public UserBuilder withAddress(Address address) {
        this.address = address;
        return this;
    }

    public UserBuilder withPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public UserBuilder withDob(String dob) {
        this.dob = dob;
        return this;
    }

    public UserBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public UserBuilder withEmail(String email) {
        this.email = email;
        return this;
    }

    public User build() {
        return new User(first_name, last_name, address, phone, dob, password, email);
    }

    private static String randomDob(Faker fake) {
        LocalDate date = LocalDate.of(
                fake.number().numberBetween(1970, 2008),
                fake.number().numberBetween(1, 12),
                fake.number().numberBetween(1, 28)
        );
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
