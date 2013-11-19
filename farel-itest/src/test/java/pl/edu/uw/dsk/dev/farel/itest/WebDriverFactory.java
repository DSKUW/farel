package pl.edu.uw.dsk.dev.farel.itest;

import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.slf4j.bridge.SLF4JBridgeHandler;

public class WebDriverFactory {

    enum SupportedBrowser {
        FIREFOX, PHANTOMJS;

        public static SupportedBrowser fromString(String value) {
            for (SupportedBrowser browser : SupportedBrowser.values()) {
                if (browser.name().equalsIgnoreCase(value)) {
                    return browser;
                }
            }
            throw new IllegalArgumentException(String.format("Cannot recognize SupportedBrowser from '%s'.", value));
        }
    }

    public static WebDriver create() {
        SupportedBrowser browser = TestEnvironmentPropertiesHelper.browser();
        switch (browser) {
            case FIREFOX: {
                return new FirefoxDriver();
            }
            case PHANTOMJS: {
                redirectJavaUtilLoggingToSlf4j();
                return new PhantomJSDriver(phantomJSCapabilities());
            }
        }
        return null;
    }

    /**
     * PhantomJSDriver uses java.util.logging for logging. During initialization
     * it shows much of unneeded (for most cases) informations.
     */
    private static void redirectJavaUtilLoggingToSlf4j() {
        if (!SLF4JBridgeHandler.isInstalled()) {
            SLF4JBridgeHandler.removeHandlersForRootLogger();
            SLF4JBridgeHandler.install();
        }
    }

    /*
     * Neat sample:
     * https://github.com/detro/ghostdriver/blob/master/test/java/src
     * /test/java/ghostdriver/BaseTest.java
     */
    private static Capabilities phantomJSCapabilities() {
        DesiredCapabilities capabilities = DesiredCapabilities.phantomjs();
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
                        TestEnvironmentPropertiesHelper.phantomjs_home());
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS, new String[] { "--webdriver-loglevel="
                        + TestEnvironmentPropertiesHelper.webDriverLogLevel() });
        capabilities.setCapability(PhantomJSDriverService.PHANTOMJS_GHOSTDRIVER_CLI_ARGS, new String[] { "--loglevel="
                        + TestEnvironmentPropertiesHelper.webDriverLogLevel() });
        return capabilities;
    }
}
