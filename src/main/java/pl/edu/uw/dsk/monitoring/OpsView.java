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
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class OpsView {

    private final static String OPSVIEW_API_URL = "https://adres.strony/rest/";
    private final static String OPSVIEW_USERNAME = "login";
    private final static String OPSVIEW_PASSWORD = "password";
    private static final String X_OPSVIEW_USERNAME_HEADER = "X-Opsview-Username";
    private static final String X_OPSVIEW_TOKEN_HEADER = "X-Opsview-Token";

    public static Token TOKEN;
    public static OpsViewResponse responseBean;

    private static RestTemplate restTemplate = new RestTemplate();
    private static ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) throws IOException {
        opsViewStatus();
        jenkinsStatus();
    }

    private static void jenkinsStatus() throws JsonParseException, JsonMappingException, IOException{
        HttpComponentsClientHttpRequestFactory customFactory = createCustomRequestFactory();
        restTemplate =  new RestTemplate(customFactory);
        String jenkinsStatus = restTemplate.getForObject("https://adres.strony/job/probad-nightly/lastBuild/api/json", String.class);
        JenkinsBean jenkinsBean = mapper.readValue(jenkinsStatus, JenkinsBean.class);
        System.out.println(jenkinsBean.getResult());
    }

    private static ContextAwareHttpComponentsClientHttpRequestFactory createCustomRequestFactory() {
        CredentialsProvider credentialsProvider = createCredentialsProvider("login", "password");
        HttpClient httpClient = HttpClientBuilder.create().setDefaultCredentialsProvider(credentialsProvider).build();
        ContextAwareHttpComponentsClientHttpRequestFactory customFactory = new ContextAwareHttpComponentsClientHttpRequestFactory(httpClient);
        customFactory.setHttpContext(createHttpContext());
        return customFactory;
    }

    private static CredentialsProvider createCredentialsProvider(String username, String password) {
        Credentials credentials = new UsernamePasswordCredentials(username, password);
        CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        credentialsProvider.setCredentials(AuthScope.ANY, credentials);
        return credentialsProvider;
    }

    private static HttpContext createHttpContext() {
        HttpContext localContext = new BasicHttpContext();
        localContext.setAttribute(HttpClientContext.AUTH_CACHE, createAuthCache());
        return localContext;
    }

    private static AuthCache createAuthCache() {
        // Create AuthCache instance
        AuthCache authCache = new BasicAuthCache();
        // Generate BASIC scheme object and add it to the local auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(getTargetHost(), basicAuth);
        return authCache;
    }

    private static HttpHost getTargetHost() {
        return new HttpHost("adres.strony", 443, "https");
    }

    private static void opsViewStatus() throws IOException {
        TOKEN = getToken();
        getVersion();
        getStatus();
        System.out.println(fetchUsefulInformationFromResponseBean(responseBean));
    }

    private static Token getToken() throws IOException {
        String tokenInJson = restTemplate.postForObject(OPSVIEW_API_URL + "login", getOpsviewCredentials(),
                        String.class);
        return parseToken(tokenInJson);
    }

    private static MultiValueMap<String, String> getOpsviewCredentials() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("username", OPSVIEW_USERNAME);
        map.add("password", OPSVIEW_PASSWORD);
        return map;
    }

    private static Token parseToken(String tokenInJson) throws IOException {
        Token tokenBean = mapper.readValue(tokenInJson, Token.class);
        return tokenBean;
    }

    private static String getVersion() {
        return restTemplate.getForObject(OPSVIEW_API_URL, String.class);
    }

    private static String getStatus() throws JsonParseException, JsonMappingException, IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(X_OPSVIEW_USERNAME_HEADER, OPSVIEW_USERNAME);
        httpHeaders.add(X_OPSVIEW_TOKEN_HEADER, TOKEN.getToken());
        HttpEntity<String> requestBody = new HttpEntity<String>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(OPSVIEW_API_URL + "/status/service?host=jenkins",
                        HttpMethod.GET, requestBody, String.class);
        String stringResponse = response.getBody();
        responseBean = mapper.readValue(stringResponse, OpsViewResponse.class);
        return stringResponse;
    }

    private static String fetchUsefulInformationFromResponseBean(OpsViewResponse responseBean) {
        String output = new String("");
        System.out.println("OPSVIEW STATUS:");
        for (int i = 0; i < 5; i++) {
            output = output.concat(responseBean.list[0].services[i].name);
            output = output.concat(":::");
            output = output.concat(responseBean.list[0].services[i].output);
            output = output.concat("\n");
        }
        return output;
    }
}

class ContextAwareHttpComponentsClientHttpRequestFactory extends HttpComponentsClientHttpRequestFactory {
    private HttpContext httpContext;
    
    public ContextAwareHttpComponentsClientHttpRequestFactory(HttpClient httpClient) {
        super(httpClient);
    }
    
    protected HttpContext createHttpContext(HttpMethod httpMethod, URI uri) {
        // Ignoring the URI and method.
        return httpContext;
    }
    
    public void setHttpContext(HttpContext httpContext) {
        this.httpContext = httpContext;
    }
}