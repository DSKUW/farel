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
import org.apache.http.impl.auth.DigestScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
//import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class CodereviewStatus {
    static private final String CODEREVIEW_API_URL = "https://adres.strony/";
    static private final String CODEREVIEW_USERNAME = "login";
    static private final String CODEREVIEW_PASSWORD = "password";
    private RestTemplate restTemplate = new RestTemplate();
    //private ObjectMapper mapper = new ObjectMapper();
    private String CodereviewStatus = null;
    //private JenCodereviewpleBean CodereviewBean = null;
    
    public void displayStatus() {
        System.out.println("CODEREVIEW INFO:");
        System.out.println(CodereviewStatus);
    }
    
    CodereviewStatus() throws JsonParseException, JsonMappingException, IOException{
        HttpComponentsClientHttpRequestFactory customFactory = createCustomRequestFactory();
        restTemplate =  new RestTemplate(customFactory);
        CodereviewStatus = restTemplate.getForObject(CODEREVIEW_API_URL + "a/projects/probad", String.class);
    }

    private ContextAwareHttpComponentsClientHttpRequestFactory createCustomRequestFactory() {
        CredentialsProvider credentialsProvider = createCredentialsProvider(CODEREVIEW_USERNAME, CODEREVIEW_PASSWORD);
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
        DigestScheme digestAuth = new DigestScheme();
        digestAuth.overrideParamter("realm", "some realm");
        digestAuth.overrideParamter("nonce", "whatever");
        authCache.put(getTargetHost(), digestAuth);
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