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

    public static String randomMiddleName() {
        return faker.name().nameWithMiddle();
    }

    public static String randomNickname() {
        return faker.name().username();
    }

    public static String randomTitle() {
        return faker.name().title();
    }

    public static String randomCompany() {
        return faker.company().name();
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

    public static String randomHomePage() {
        return faker.internet().url();
    }

    public static String randomFax() {
        return faker.phoneNumber().cellPhone();
    }

    public static String randomPhoto() {
        File dir = new File("addressbook_web_tests/src/test/resources/images");
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return null; // если папки нет или она пуста
        }
        return files[new Random().nextInt(files.length)].getAbsolutePath();
    }

    public static ContactsData randomContactsData() {
        return new ContactsData(
                "",
                randomFirstName(),
                randomLastName(),
                randomMiddleName(),
                randomNickname(),
                randomTitle(),
                randomCompany(),
                randomAddress(),
                randomHomePhone(),
                randomMobilePhone(),
                randomWorkPhone(),
                randomEmail(),
                randomEmail2(),
                randomEmail3(),
                randomHomePage(),
                randomPhoto(),
                randomFax()

        );
    }

}
