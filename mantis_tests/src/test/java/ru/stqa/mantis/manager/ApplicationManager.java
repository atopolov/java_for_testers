package ru.stqa.mantis.manager;

import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.util.Optional;
import java.util.Properties;

public class ApplicationManager {

    private static final Dimension WINDOW_SIZE = new Dimension(1650, 818);
    private static final int IMPLICIT_WAIT_SECONDS = 5;

    private WebDriver driver;
    private String browser;
    private Properties properties;
    private SessionHelper sessionHelper;
    private HttpSessionHelper httpSessionHelper;
    private JamesCliHelper jamesCliHelper;
    private MailHelper mailHelper;
    private RegistrationHelper registrationHelper;

    public void init(String browser, Properties properties) {
        this.properties = properties;
        this.browser = browser;
    }

    public WebDriver driver() {
        if (driver == null) {
            driver = createDriver(browser);
            registerShutdownHook();
            configureDriver();
            openHomePage();
        }
        return driver;
    }

    public void openHomePage() {
        driver().get(properties.getProperty("web.baseUrl"));
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
            } catch (Exception ignored) {
            }
        }));
    }

    public SessionHelper session() {
        return Optional.ofNullable(sessionHelper)
                .orElseGet(() -> {
                    sessionHelper = new SessionHelper(this);
                    return sessionHelper;
                });
    }

    public HttpSessionHelper http() {
        return Optional.ofNullable(httpSessionHelper)
                .orElseGet(() -> {
                    httpSessionHelper = new HttpSessionHelper(this);
                    return httpSessionHelper;
                });
    }

    public String property(String name){
        return properties.getProperty(name);
    }

    public JamesCliHelper jamesCli() {
        return Optional.ofNullable(jamesCliHelper)
                .orElseGet(() -> {
                    jamesCliHelper = new JamesCliHelper(this);
                    return jamesCliHelper;
                });
    }

    public MailHelper mail() {
        return Optional.ofNullable(mailHelper)
                .orElseGet(() -> {
                    mailHelper = new MailHelper(this);
                    return mailHelper;
                });
    }

    public RegistrationHelper registration() {
        return Optional.ofNullable(registrationHelper)
                .orElseGet(() -> {
                    registrationHelper = new RegistrationHelper(this);
                    return registrationHelper;
                });
    }

}
