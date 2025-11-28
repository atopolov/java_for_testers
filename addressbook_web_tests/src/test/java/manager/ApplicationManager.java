package manager;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

import java.time.Duration;
import java.util.Properties;

public class ApplicationManager {

    private static final Dimension WINDOW_SIZE = new Dimension(1650, 818);
    private static final int IMPLICIT_WAIT_SECONDS = 5;

    protected WebDriver driver;
    private LoginHelper loginHelper;
    private GroupHelper groupHelper;
    private ContactHelper contactHelper;
    private Properties properties;
    private JdbcHelper jdbcHelper;
    private HibernateHelper hbm;

    public void init(String browser, Properties properties) {
        this.properties = properties;


        if (driver != null) {
            return;
        }

        driver = createDriver(browser);
        registerShutdownHook();

        configureDriver();
        driver.get(properties.getProperty("web.baseUrl"));

        session().login(properties.getProperty("web.username"), properties.getProperty("web.password"));
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

    public JdbcHelper jdbcHelper() {
        if (jdbcHelper == null) {
            jdbcHelper = new JdbcHelper(this);
        }
        return jdbcHelper;
    }

    public HibernateHelper hbm() {
        if (hbm == null) {
            hbm = new HibernateHelper(this);
        }
        return hbm;
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
