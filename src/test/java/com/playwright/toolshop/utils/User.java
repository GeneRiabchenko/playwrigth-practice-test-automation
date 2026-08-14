package com.playwright.toolshop.utils;

/*
{
        "first_name": "John",
        "last_name": "Doe",
        "address": {
            "street": "Street 1",
            "house_number": "12",
            "city": "City",
            "state": "State",
            "country": "Country",
            "postal_code": "1234AA"
        },
        "phone": "0987654321",
        "dob": "1970-01-01",
        "password": "SuperSecure@123",
        "email": "john@doe.example"
        }
*/

public record User(
        String first_name,
        String last_name,
        Address address,
        String phone,
        String dob,
        String password,
        String email
) {}