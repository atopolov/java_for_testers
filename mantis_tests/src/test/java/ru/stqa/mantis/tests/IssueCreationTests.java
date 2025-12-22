package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Test;
import ru.stqa.mantis.generator.IssueDataGen;

public class IssueCreationTests extends TestBase {

    @Test
    public void canCreateIssue() {

        app.soap().createIssue(IssueDataGen.randomIssueData()
                .withProject(1L));
    }
}
