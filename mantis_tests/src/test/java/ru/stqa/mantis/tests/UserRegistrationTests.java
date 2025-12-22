package ru.stqa.mantis.tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.stqa.mantis.generator.EmailData;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserRegistrationTests extends TestBase {

    @Test
    @DisplayName("Регистрация пользователя через REST API")
    public void canRegisterUserViaRest() throws InterruptedException {

        String username = EmailData.randomUsername();
        String email = username + "@localhost";
        String password = "password";

        System.out.println("=== Начало теста регистрации пользователя ===");
        System.out.println("Сгенерированный username: " + username);
        System.out.println("Сгенерированный email: " + email);

        System.out.println("Очистка почтового ящика...");
        app.mail().deleteAll(email, password);

        System.out.println("Создание пользователя на James через REST API...");
        app.jamesApi().addUser(email, password);
        System.out.println("Пользователь создан: " + email);

        System.out.println("Начало регистрации в Mantis через REST API...");
        app.rest().startRegistration(username, email);
        System.out.println("Регистрация пользователя инициирована в Mantis: " + username);

        System.out.println("Ожидание письма с подтверждением...");
        app.mail().receive(email, password, Duration.ofSeconds(60));
        String confirmationLink = app.mail().getLinkFromLastMail(email, password);
        System.out.println("Ссылка для подтверждения регистрации: " + confirmationLink);

        System.out.println("Завершение регистрации пользователя...");
        app.registration().finish(confirmationLink, password);
        assertTrue(app.registration().isFinished(), "Регистрация не завершена!");
        System.out.println("Регистрация завершена успешно.");

        System.out.println("Проверка входа в систему с новым пользователем...");
        app.http().login(username, password);
        assertTrue(app.http().isLoggedIn(), "Вход в систему не удался!");
        System.out.println("Вход выполнен успешно.");
        System.out.println("=== Тест завершён ===");
    }
}
