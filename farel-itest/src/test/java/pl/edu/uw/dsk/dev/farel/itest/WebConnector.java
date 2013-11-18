package pl.edu.uw.dsk.dev.farel.itest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebConnector {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebConnector.class);
    private final static long DEFAULT_TIMEOUT_IN_MS = 2000;
    public WebDriver driver = new FirefoxDriver();

    public WebConnector() {
        LOGGER.info("Initializing driver");
    }

    public void clickAndWait(String selector) {
        WebElement element = driver.findElement(By.id(selector));
        element.click();
        driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT_IN_MS, TimeUnit.MILLISECONDS);
    }

    public void open(String location) {
        LOGGER.info("Accessing page");
        driver.get(location);
        // driver.manage().timeouts().implicitlyWait(DEFAULT_TIMEOUT_IN_MS,
        // TimeUnit.MILLISECONDS);
    }

    public boolean isTextPresent(String text) {
        WebElement content = driver.findElement(By.tagName("body"));
        return content.getText().contains(text);
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
