package pl.edu.uw.dsk.dev.farel.information_source.continuous_integration;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import pl.edu.uw.dsk.dev.farel.entites.continuous_integration.BuildStatus;
import pl.edu.uw.dsk.dev.farel.exceptions.TechnicalException;
import pl.edu.uw.dsk.dev.farel.utils.ContextAwareHttpComponentsClientHttpRequestFactory;
import pl.edu.uw.dsk.dev.farel.utils.HttpHostFactory;
import pl.edu.uw.dsk.dev.farel.utils.LoginInfo;

public class JenkinsManager {
 
    private static final Logger LOGGER = LoggerFactory.getLogger(JenkinsManager.class);

    private final String baseUrl;
    private final String username;
    private final String password;

    private RestTemplate restTemplate;
    private ObjectMapper json2ObjectMapper = new ObjectMapper();

    public JenkinsManager(String baseUrl, LoginInfo loginInfo) {
        this.baseUrl = baseUrl;
        this.username = loginInfo.getUsername();
        this.password = loginInfo.getPassword();
        this.restTemplate = createPreAuthenticatedRestTemplate();
    }

    public BuildStatus getStatus(String projectName) throws TechnicalException {
        String jenkinsStatus = restTemplate.getForObject(this.baseUrl + "job/" + projectName + "/lastBuild/api/json",
                        String.class);
        return parseObjectInJson(jenkinsStatus, BuildStatus.class);
    }

    private RestTemplate createPreAuthenticatedRestTemplate() {
        CredentialsProvider credentialsProvider = createCredentialsProvider(username, password);
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
        ContextAwareHttpComponentsClientHttpRequestFactory customFactory = new ContextAwareHttpComponentsClientHttpRequestFactory(
                        httpClient);
        customFactory.setHttpContext(createHttpContext());
        return new RestTemplate(customFactory);
    }

    private CredentialsProvider createCredentialsProvider(String username, String password) {
        Credentials credentials = new UsernamePasswordCredentials(username, password);
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);
        return credentialsProvider;
    }

    private HttpContext createHttpContext() {
        HttpContext localContext = new BasicHttpContext();
        localContext.setAttribute(HttpClientContext.AUTH_CACHE, createAuthCache());
        return localContext;
    }

    private AuthCache createAuthCache() {
        AuthCache authCache = new BasicAuthCache();
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(getTargetHost(), basicAuth);
        return authCache;
    }

    private HttpHost getTargetHost() {
        return HttpHostFactory.fromString(baseUrl);
    }

    public <T> T parseObjectInJson(String objectInJson, Class<T> objectClass) throws TechnicalException {
        try {
            return json2ObjectMapper.readValue(objectInJson, objectClass);
        } catch (IOException e) {
            LOGGER.error("Error durning parsing object of class '{}' from response '{}'", objectClass, objectInJson, e);
            throw new TechnicalException(e);
        }
    }

}