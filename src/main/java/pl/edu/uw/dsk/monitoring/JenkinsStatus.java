package pl.edu.uw.dsk.monitoring;

import java.io.IOException;
import java.net.URI;

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
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class JenkinsStatus {
    static private final String JENKINS_API_URL = "https://adres.strony/";
    static private final String JENKINS_USERNAME = "login";
    static private final String JENKINS_PASSWORD = "password";
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper mapper = new ObjectMapper();
    private String jenkinsStatus = null;
    private JenkinsSimpleBean jenkinsBean = null;
    
    public void displayStatus() {
        System.out.println("JENKIN'S LAST BUILD STATUS:");
        System.out.println(jenkinsBean.getResult());
    }
    
    JenkinsStatus() throws JsonParseException, JsonMappingException, IOException{
        HttpComponentsClientHttpRequestFactory customFactory = createCustomRequestFactory();
        restTemplate =  new RestTemplate(customFactory);
        jenkinsStatus = restTemplate.getForObject(JENKINS_API_URL + "job/probad-nightly/lastBuild/api/json", String.class);
        jenkinsBean = mapper.readValue(jenkinsStatus, JenkinsSimpleBean.class);
    }

    private ContextAwareHttpComponentsClientHttpRequestFactory createCustomRequestFactory() {
        CredentialsProvider credentialsProvider = createCredentialsProvider(JENKINS_USERNAME, JENKINS_PASSWORD);
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
        ContextAwareHttpComponentsClientHttpRequestFactory customFactory = new ContextAwareHttpComponentsClientHttpRequestFactory(httpClient);
        customFactory.setHttpContext(createHttpContext());
        return customFactory;
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
        return new HttpHost("adres.strony", 443, "https");
    }

    private class ContextAwareHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {
        private HttpContext httpContext;
        
        public ContextAwareHttpComponentsClientHttpRequestFactory(HttpClient httpClient) {
            super(httpClient);
        }
        
        protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
            return httpContext;
        }
        
        public void setHttpContext(HttpContext httpContext) {
            this.httpContext = httpContext;
        }
    }
}