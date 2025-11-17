package model;

import com.github.javafaker.Faker;

public class GroupDataGenerator {

    private static final Faker faker = new Faker();

    public static String randomGroupName() {
        return faker.team().name();
    }

    public static String randomGroupHeader() {
        return faker.lorem().sentence(1);
    }

    public static String randomGroupFooter() {
        return faker.lorem().sentence(1);
    }

    public static GroupData randomGroup() {
        return new GroupData(randomGroupName(), randomGroupHeader(), randomGroupFooter());
    }
}