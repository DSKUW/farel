package pl.edu.uw.dsk.dev.farel.auxilliary;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PropertiesHelper {

    public static class MessagesHelper {
        
        private static final Properties MESSAGES = PropertiesHelper.fileProperties("/messages_pl.properties");
        
        public static String message(String key) {
            return PropertiesHelper.getProperty(MESSAGES, key);
        }
        
        // XXX-ach: Not so clean hack, try looking for better solution
        @SuppressWarnings({ "unchecked", "rawtypes" })
        public static Map<String, String> messages() {
            return new HashMap<String, String>((Map) MESSAGES);
        }
    }
    
    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesHelper.class);

    public static Properties fileProperties(String propertiesFilename) {
        try {
            InputStream inStream = PropertiesHelper.class.getResourceAsStream(propertiesFilename);
            Properties props = new Properties();
            props.load(inStream);
            return props;
        } catch (IOException e) {
            LOGGER.error("Cannot read properties from file {}.", propertiesFilename, e);
            return new Properties();
        }
    }

    public static String environementProperty(String propertyName, String defaultValue) {
        try {
            return environementProperty(propertyName);
        } catch (IllegalArgumentException e) {
            return defaultValue;
        }
    }

    public static String environementProperty(String propertyName) {
        String property = System.getenv(propertyName);
        if (property == null || property.isEmpty()) {
            throw new IllegalArgumentException(String.format("Property '%s' not found.", propertyName));
        }
        return property;

    }

    public static String getProperty(Properties properties, String key) {
        if (!properties.containsKey(key)) {
            throw new IllegalArgumentException(String.format("Property '%s' not found.", key));
        }
        return properties.getProperty(key);
    }

}
