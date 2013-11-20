package pl.edu.uw.dsk.dev.farel.itest.browser_config;

import pl.edu.uw.dsk.dev.farel.auxilliary.PropertiesHelper;
import pl.edu.uw.dsk.dev.farel.itest.browser_config.WebDriverFactory.SupportedBrowser;


public class TestEnvironmentPropertiesHelper {

    public static SupportedBrowser browser() {
        String propertyValue = PropertiesHelper.environementProperty("FAREL_BROWSER");
        return SupportedBrowser.fromString(propertyValue);
    }

    public static String phantomjs_home() {
        return PropertiesHelper.environementProperty("PHANTOMJS_HOME");
    }

    public static String serverUrl() {
        return PropertiesHelper.environementProperty("FAREL_URL");
    }

    public static String webDriverLogLevel() {
        return PropertiesHelper.environementProperty("WEBDRIVER_LOGLEVEL", "ERROR");
    }
}
