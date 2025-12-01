package manager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.nio.file.Paths;
import java.time.Duration;

public class HelperBase {

    protected final ApplicationManager manager;
    private final Duration TIMEOUT = Duration.ofSeconds(5);

    public HelperBase(ApplicationManager manager) {
        this.manager = manager;
    }

    protected void click(By locator) {
        WebElement element = waitForClickable(locator);
        element.click();
    }

    protected void type(By locator, String text) {
        if (text == null) {
            text = "";
        }
        WebElement element = waitForVisible(locator);
        element.clear();
        element.sendKeys(text);
    }

    protected void attach(By locator, String file) {
        WebElement element = waitForVisible(locator);
        element.sendKeys(Paths.get(file).toAbsolutePath().toString());
    }

    private WebElement waitForClickable(By locator) {
        return new WebDriverWait(manager.driver, TIMEOUT)
                .until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement waitForVisible(By locator) {
        return new WebDriverWait(manager.driver, TIMEOUT)
                .until(ExpectedConditions.visibilityOfElementLocated(locator));
    }
}

