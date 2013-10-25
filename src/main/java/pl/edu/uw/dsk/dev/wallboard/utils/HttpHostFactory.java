package pl.edu.uw.dsk.dev.wallboard.utils;

import org.apache.http.HttpHost;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.uw.dsk.dev.wallboard.exceptions.TechnicalException;

public class HttpHostFactory {

    private static final Logger LOGGER = LoggerFactory.getLogger(HttpHostFactory.class);

    /**
     * @throws TechnicalException TODO
     */
    public static HttpHost fromString(String url) throws TechnicalException {
        LOGGER.info("Creating HttpHost from given string: '{}'.", url);

        String[] splittedUrl = url.split("://");
        String scheme = splittedUrl[0];
        int portNumber = parsePortNumber(scheme);
        String hostname = removeEndingSlash(splittedUrl[1]);

        return new HttpHost(hostname, portNumber, scheme);
    }

    /**
     * @throws TechnicalException TODO
     */
    private static int parsePortNumber(String protocol) throws TechnicalException {
        switch (protocol) {
            case ("http"): {
                return 80;
            }
            case ("https"): {
                return 443;
            }
            default: {
                LOGGER.error("Invalid protocol was provided: '{}'.", protocol);
                throw new TechnicalException("Invalid protocol was provided.");
            }
        }
    }

    private static String removeEndingSlash(String address) {
        return address.endsWith("/") ? address.substring(0, address.length() - 1) : address;
    }

}