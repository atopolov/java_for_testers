package ru.stqa.mantis.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.stqa.mantis.generator.EmailData;

import java.time.Duration;

public class UserRegistrationTests extends TestBase {

    @Test
    @DisplayName("Регистрация пользователя")
    public void canRegisterUser() throws InterruptedException {
        var email = String.format("%s@localhost", EmailData.randomEmail());
        var username = EmailData.randomEmail();
        var password = "password";

        app.jamesCli().addUser(email, password);
        app.session().registerUser(email, password);
        app.mail().receive(email, password, Duration.ofSeconds(60));
        String link = app.mail().getLinkFromLastMail(email, password);
        app.session().finishRegistration(link,password);

        app.http().login(username, password);
    }
}
