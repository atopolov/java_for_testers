package ru.stqa.mantis.generator;

import com.github.javafaker.Faker;
import ru.stqa.mantis.model.IssueData;

public class IssueDataGen {

    public static final Faker faker = new Faker();

    public static IssueData randomIssueData() {
        return new IssueData()
                .withSummary(faker.lorem().sentence(5))
                .withDescription(faker.lorem().paragraph(3));
    }
}

