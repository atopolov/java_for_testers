package ru.stqa.mantis.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.stqa.mantis.generator.EmailData;

import java.io.IOException;

public class JamesTests extends TestBase {

    @Test
    @DisplayName("Создание пользователя")
    public void canCreateUser() throws InterruptedException, IOException {
        app.jamesCli().addUser(String.format("%s@localhost", EmailData.randomUsername()),
                "password");
    }
}
