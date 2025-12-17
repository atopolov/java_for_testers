package ru.stqa.mantis.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.stqa.mantis.generator.EmailData;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRegistrationTests extends TestBase {

    @Test
    @DisplayName("Регистрация пользователя")
    public void canRegisterUser() throws InterruptedException {

        String username = EmailData.randomUsername();
        String email = username + "@localhost";
        String password = "password";

        app.mail().deleteAll(email, password);

        app.jamesCli().addUser(email, password);

        app.registration().start(username,email);

        app.mail().receive(email, password, Duration.ofSeconds(60));
        String link = app.mail().getLinkFromLastMail(email, password);

        app.registration().finish(link, password);
        assertTrue(app.registration().isFinished());

        app.http().login(username, password);
        assertTrue(app.http().isLoggedIn());
    }
}
