package pl.edu.uw.dsk.dev.farel.information_source.code_review;

import java.io.IOException;

import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpClient;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import pl.edu.uw.dsk.dev.farel.entites.code_review.ProjectStatus;
import pl.edu.uw.dsk.dev.farel.exceptions.TechnicalException;
import pl.edu.uw.dsk.dev.farel.utils.ContextAwareHttpComponentsClientHttpRequestFactory;
import pl.edu.uw.dsk.dev.farel.utils.HttpHostFactory;
import pl.edu.uw.dsk.dev.farel.utils.LoginInfo;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CodeReviewManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(CodeReviewManager.class);

    private String baseUrl;
    private String username;
    private String password;

    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper json2ObjectMapper = new ObjectMapper();
    private ProjectStatus projectStatus;

    public CodeReviewManager(String baseUrl, LoginInfo loginInfo) {
        this.username = loginInfo.getUsername();
        this.password = loginInfo.getPassword();
        this.baseUrl = baseUrl;
    }

    public ProjectStatus getStatus(String projectName) {
        setupContext();
        String codereviewStatus = restTemplate.getForObject(this.baseUrl + "a/projects/" + projectName, String.class);
        codereviewStatus = removeMagicChars(codereviewStatus);
        projectStatus = parseObjectInJson(codereviewStatus, ProjectStatus.class);
        return projectStatus;
    }

    private String removeMagicChars(String magicString) {
        return magicString.substring(magicString.indexOf("{"));
    }

    private void setupContext() {
        CredentialsProvider credentialsProvider = createCredentialsProvider(username, password);
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
        ContextAwareHttpComponentsClientHttpRequestFactory customFactory = new ContextAwareHttpComponentsClientHttpRequestFactory(
                        httpClient);
        customFactory.setHttpContext(createHttpContext());
        restTemplate = new RestTemplate(customFactory);
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
        DigestScheme digestAuth = new DigestScheme();
        authCache.put(getTargetHost(), digestAuth);
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