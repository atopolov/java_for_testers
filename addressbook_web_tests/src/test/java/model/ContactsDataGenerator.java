package model;

import com.github.javafaker.Faker;
import java.io.File;

import java.util.Random;

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

    public static String randomMobilePhone() {
        return faker.phoneNumber().cellPhone();
    }

    public static String randomWorkPhone() {
        return faker.phoneNumber().phoneNumber();
    }

    public static String randomEmail() {
        return faker.internet().emailAddress();
    }

    public static String randomEmail2() {
        return faker.internet().emailAddress();
    }

    public static String randomEmail3() {
        return faker.internet().emailAddress();
    }

    public static String randomAddress2() {
        return faker.address().secondaryAddress();
    }

    public static String randomPhone2() {
        return faker.phoneNumber().phoneNumber();
    }

    public static String randomNotes() {
        return faker.lorem().sentence();
    }

    public static String randomPhoto() {
        File[] files = new File("src/test/resources/images").listFiles();

        return files[new Random().nextInt(files.length)].getAbsolutePath();
    }

    public static ContactsData randomContactsData() {
        return new ContactsData(
                "",
                randomFirstName(),
                randomLastName(),
                randomAddress(),
                randomHomePhone(),
                randomMobilePhone(),
                randomWorkPhone(),
                randomEmail(),
                randomEmail2(),
                randomEmail3(),
                randomAddress2(),
                randomPhone2(),
                randomNotes(),
                randomPhoto()
        );
    }

}
