package ru.stqa.mantis.generator;

import com.github.javafaker.Faker;

public class EmailData {

    private static final Faker faker = new Faker();

    private EmailData() {}

    public static String randomEmail() {
        return faker.lorem().word();
    }

}
