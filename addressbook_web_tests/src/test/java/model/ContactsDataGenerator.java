package model;

import com.github.javafaker.Faker;

public class ContactsDataGenerator {

    private static final Faker faker = new Faker();

    private ContactsDataGenerator() {
    }

    public static String randomFirstName() {
        return faker.name().firstName();
    }

    public static String randomLastName() {
        return faker.name().lastName();
    }

    public static String randomAddress() {
        return faker.address().fullAddress();
    }

    public static String randomHomePhone() {
        return faker.phoneNumber().cellPhone();
    }

    public static String randomEmail() {
        return faker.internet().emailAddress();
    }

    public static ContactsData randomContactsData() {
        return new ContactsData(
                "",
                randomFirstName(),
                randomLastName(),
                randomAddress(),
                randomHomePhone(),
                randomEmail()
        );
    }

}
