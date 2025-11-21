package manager;

import org.openqa.selenium.By;

public class LoginHelper extends HelperBase {

    private final By usernameField = By.name("user");
    private final By passwordField = By.name("pass");
    private final By loginButton   = By.xpath("//input[@value='Login']");

    public LoginHelper(ApplicationManager manager) {
        super(manager);
    }

    public void login(String user, String password) {
        type(usernameField, user);
        type(passwordField, password);
        click(loginButton);
    }
}