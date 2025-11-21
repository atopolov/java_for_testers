package manager;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;

public class ApplicationManager {

    private static final String BASE_URL = "http://localhost/addressbook/";
    private static final Dimension WINDOW_SIZE = new Dimension(1650, 818);
    private static final int IMPLICIT_WAIT_SECONDS = 5;

    protected WebDriver driver;
    private LoginHelper loginHelper;
    private GroupHelper groupHelper;
    private ContactHelper contactHelper;

    public void init(String browser) {
        if (driver != null) {
            return;
        }

        driver = createDriver(browser);
        registerShutdownHook();

        configureDriver();
        driver.get(BASE_URL);

        session().login("admin", "secret");
    }

    private WebDriver createDriver(String browser) {
        return switch (browser.toLowerCase()) {
            case "firefox" -> new FirefoxDriver();
            case "chrome" -> new ChromeDriver();
            default -> throw new IllegalArgumentException(
                    String.format("Unsupported browser: %s", browser)
            );
        };
    }

    private void configureDriver() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(IMPLICIT_WAIT_SECONDS));
        driver.manage().window().setSize(WINDOW_SIZE);
    }

    private void registerShutdownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                if (driver != null) driver.quit();
            } catch (Exception ignored) {}
        }));
    }

    public LoginHelper session() {
        if (loginHelper == null) {
            loginHelper = new LoginHelper(this);
        }
        return loginHelper;
    }

    public GroupHelper groups() {
        if (groupHelper == null) {
            groupHelper = new GroupHelper(this);
        }
        return groupHelper;
    }

    public ContactHelper contacts() {
        if (contactHelper == null) {
            contactHelper = new ContactHelper(this);
        }
        return contactHelper;
    }

    public boolean isElementPresent(By locator) {
        try {
            driver.findElement(locator);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }
}
