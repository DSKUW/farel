package pl.edu.uw.dsk.dev.wallboard.code_review;

import java.net.URI;

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
//import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import pl.edu.uw.dsk.dev.wallboard.LoginInfo;

public class CodeReviewManager {

    private String baseUrl;
    private String username;
    private String password;
    
    private RestTemplate restTemplate = new RestTemplate();
    //private ObjectMapper mapper = new ObjectMapper();
    private String codereviewStatus;
    //private JenCodereviewpleBean CodereviewBean = null;

    public CodeReviewManager(String baseUrl, LoginInfo loginInfo) {
        this.username = loginInfo.getUsername();
        this.password = loginInfo.getPassword();
        this.baseUrl = baseUrl;
    }
    public String getStatus(String projectName) {
        setupContext();
        codereviewStatus = restTemplate.getForObject(this.baseUrl + "a/projects/" + projectName, String.class);
        //tworzenie beana
        return codereviewStatus;
    }
    private void setupContext() {
        CredentialsProvider credentialsProvider = createCredentialsProvider(username, password);
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
        ContextAwareHttpComponentsClientHttpRequestFactory customFactory = new ContextAwareHttpComponentsClientHttpRequestFactory(httpClient);
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
        digestAuth.overrideParamter("realm", "test");
        digestAuth.overrideParamter("nonce", "");
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