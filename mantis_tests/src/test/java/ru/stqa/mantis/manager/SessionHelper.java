package ru.stqa.mantis.manager;

import org.openqa.selenium.By;

public class SessionHelper extends HelperBase {

    private static final By USERNAME_FIELD = By.name("username");
    private static final By PASSWORD_FIELD = By.name("password");
    private static final By SUBMIT_BUTTON = By.cssSelector("input[type='submit']");
    private static final By LOGGED_USER_INFO = By.cssSelector("span.user-info");

    public SessionHelper(ApplicationManager manager) {
        super(manager);
    }

    public void login(String username, String password) {
        type(USERNAME_FIELD, username);
        click(SUBMIT_BUTTON);
        type(PASSWORD_FIELD, password);
        click(SUBMIT_BUTTON);
    }

    public boolean isLoggedIn() {
        return isElementPresent(LOGGED_USER_INFO);
    }
}
