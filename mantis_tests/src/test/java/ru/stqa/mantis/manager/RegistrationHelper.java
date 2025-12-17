package ru.stqa.mantis.manager;

import org.openqa.selenium.By;

public class RegistrationHelper extends HelperBase {

    private static final By USERNAME_FIELD = By.name("username");
    private static final By EMAIL_FIELD = By.name("email");
    private static final By PASSWORD_FIELD = By.name("password");
    private static final By PASSWORD_CONFIRM_FIELD = By.name("password_confirm");
    private static final By SUBMIT_BUTTON = By.cssSelector("input[type='submit']");
    private static final By FINISH_BUTTON = By.cssSelector("button");
    private static final String LOGIN_PAGE = "login_page.php";
    private static final By SIGNUP_LINK = By.linkText("Signup for a new account");

    public RegistrationHelper(ApplicationManager manager) {
        super(manager);
    }

    public void start(String username, String email) {
        manager.openHomePage();
        click(SIGNUP_LINK);
        type(USERNAME_FIELD, username);
        type(EMAIL_FIELD, email);
        click(SUBMIT_BUTTON);
    }

    public boolean isFinished() {
        return manager.driver().getCurrentUrl()
                .contains(LOGIN_PAGE);
    }

    public void finish(String confirmationLink, String password) throws InterruptedException {
        manager.driver().get(confirmationLink);
        setPassword(password);
    }

    private void setPassword(String password) {
        type(PASSWORD_FIELD, password);
        type(PASSWORD_CONFIRM_FIELD, password);
        click(FINISH_BUTTON);
    }
}