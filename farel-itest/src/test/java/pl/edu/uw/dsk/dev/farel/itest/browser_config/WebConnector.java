package pl.edu.uw.dsk.dev.farel.itest.browser_config;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebConnector.class);
    private final static long DEFAULT_TIMEOUT_IN_MS = 2000;
    public WebDriver driver;

    public WebConnector() {
        driver = WebDriverFactory.create();
        LOGGER.info("Initializing driver");
    }

    public void clickAndWait(String selector) {
        waitUntil(2000, ExpectedConditions.visibilityOf(driver.findElement(By.id(selector))));
        WebElement element = driver.findElement(By.id(selector));
        element.click();
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS);
    }

    public void fill(String id, String text) throws InterruptedException {
        waitUntil(2000, ExpectedConditions.visibilityOf(driver.findElement(By.id(id))));
        WebElement content = driver.findElement(By.id(id));
        content.sendKeys(text);
    }

    public void open(String location) {
        LOGGER.info("Accessing page");
        driver.get(location);
        // driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT_IN_MS,
        // TimeUnit.MILLISECONDS);
    }

    public boolean isTextPresent(String text) {
        WebElement content = driver.findElement(By.tagName("body"));
        String contentString = content.getText();
        return contentString.contains(text);
    }

    public boolean isTextPresentAtId(String text, String id) {
        WebElement content = driver.findElement(By.id(id));
        String contentString = content.getText();
        return contentString.contains(text);
    }

    private void waitUntil(long timeInSeconds, ExpectedCondition<?> conditions) {
        new WebDriverWait(driver, timeInSeconds).until(conditions);
    }

    public void close() {
        LOGGER.info("Closing driver");
        driver.close();
    }

    public void quit() {
        LOGGER.info("Quitting");
        driver.quit();
    }

}
