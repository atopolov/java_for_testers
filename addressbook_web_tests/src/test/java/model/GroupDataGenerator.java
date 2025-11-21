package model;

import com.github.javafaker.Faker;

public class GroupDataGenerator {

    private static final Faker faker = new Faker();

    public static String randomGroupName() {
        return faker.lorem().word();
    }

    public static String randomGroupHeader() {
        return faker.lorem().sentence();
    }

    public static String randomGroupFooter() {
        return faker.lorem().sentence();
    }

    public static GroupData randomGroup() {
        return new GroupData()
                .withName(randomGroupName())
                .withHeader(randomGroupHeader())
                .withFooter(randomGroupFooter());
    }
}
