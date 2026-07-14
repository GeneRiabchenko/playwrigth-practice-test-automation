package com.playwright.toolshop.utils;

import net.datafaker.Faker;

/*
"address": {
            "street": "Street 1",
            "house_number": "12",
            "city": "City",
            "state": "State",
            "country": "Country",
            "postal_code": "1234AA"
        },
 */
public record Address(
        String street,
        String house_number,
        String city,
        String state,
        String country,
        String postal_code
) {
    public static Address randomAddress(){
        Faker fake = new Faker();

        return new Address(
                fake.address().streetName(),
                fake.address().buildingNumber(),
                fake.address().city(),
                fake.address().state(),
                fake.address().country(),
                fake.address().postcode()
        );
    }
}


