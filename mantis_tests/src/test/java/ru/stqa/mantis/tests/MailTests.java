package ru.stqa.mantis.tests;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.regex.Pattern;

public class MailTests extends TestBase {

    @Test
    @DisplayName("Получение письма")
    public void canReceiveMail() {
        var messages = app.mail().receive("user1@localhost", "password", Duration.ofSeconds(60));
        Assertions.assertEquals(1, messages.size());
        System.out.println(messages);
    }

    @Test
    @DisplayName("Удаление всех писем")
    public void canDeleteAllMail() {
        app.mail().deleteAll("user1@localhost", "password");
    }

    @Test
    @DisplayName("Извлечение ссылки из письма")
    public void canExtractLink() {
        var messages = app.mail().receive("user1@localhost", "password", Duration.ofSeconds(60));
        var text = messages.get(0).content();
        Pattern pattern = Pattern.compile("http://\\S+");
        var matcher = pattern.matcher(text);
        if (matcher.find()) {
            var link = text.substring(matcher.start(), matcher.end());
            System.out.println(link);
        }
    }
}
