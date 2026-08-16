package com.playwright.toolshop.utils;

import net.datafaker.Faker;

import static com.playwright.toolshop.testresources.Resources.DEFAULT_COUNTRY;

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
                DEFAULT_COUNTRY,
                fake.address().postcode()
        );
    }

    public Address withStreet(String street){
        return new Address(street, house_number, city, state, country, postal_code);
    }

    public Address withHouseNumber(String house_number){
        return new Address(street, house_number, city, state, country, postal_code);
    }

    public Address withCity(String city){
        return new Address(street, house_number, city, state, country, postal_code);
    }

    public Address withState(String state){
        return new Address(street, house_number, city, state, country, postal_code);
    }

    public Address withCountry(String country){
        return new Address(street, house_number, city, state, country, postal_code);
    }

    public Address withPostalCode(String postal_code){
        return new Address(street, house_number, city, state, country, postal_code);
    }
}


