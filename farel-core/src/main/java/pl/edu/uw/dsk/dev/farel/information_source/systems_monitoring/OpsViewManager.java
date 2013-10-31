package pl.edu.uw.dsk.dev.farel.information_source.systems_monitoring;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import pl.edu.uw.dsk.dev.farel.exceptions.TechnicalException;
import pl.edu.uw.dsk.dev.farel.information_source.systems_monitoring.entities.HostStatus;
import pl.edu.uw.dsk.dev.farel.information_source.systems_monitoring.entities.OpsViewToken;
import pl.edu.uw.dsk.dev.farel.utils.LoginInfo;

public class OpsViewManager {

    private static final Logger LOGGER = LoggerFactory.getLogger(OpsViewManager.class);

    private static final String USERNAME_HEADER = "X-Opsview-Username";
    private static final String TOKEN_HEADER = "X-Opsview-Token";

    private final String baseUrl;
    private String username;
    private String password;

    private OpsViewToken token;
    private HttpEntity<String> authorizedRequest;
    private HostStatus hostStatus;

    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper json2ObjectMapper = new ObjectMapper();

    public OpsViewManager(String baseUrl, LoginInfo loginInfo) {
        this.username = loginInfo.getUsername();
        this.password = loginInfo.getPassword();
        this.baseUrl = baseUrl;
    }

    public String getVersion() {
        return restTemplate.getForObject(baseUrl, String.class);
    }

    public HostStatus getStatus(String hostName) {
        if (isTokenInvalid()) {
            obtainToken();
            createAuthorizedRequest();
        }
        ResponseEntity<String> response = restTemplate.exchange(baseUrl + "/status/service?host=" + hostName,
                        HttpMethod.GET, authorizedRequest, String.class);
        hostStatus = parseObjectInJson(response.getBody(), HostStatus.class);
        return hostStatus;
    }

    private boolean isTokenInvalid() {
        return null == token || token.isExpired();
    }

    private void createAuthorizedRequest() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(USERNAME_HEADER, username);
        httpHeaders.add(TOKEN_HEADER, token.getToken());
        authorizedRequest = new HttpEntity<String>(httpHeaders);
    }

    private OpsViewToken obtainToken() {
        String tokenInJson = restTemplate.postForObject(baseUrl + "login", getOpsviewCredentials(), String.class);
        token = parseObjectInJson(tokenInJson, OpsViewToken.class);
        return token;
    }

    private MultiValueMap<String, String> getOpsviewCredentials() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("username", username);
        map.add("password", password);
        return map;
    }

    private <T> T parseObjectInJson(String objectInJson, Class<T> objectClass) throws TechnicalException {
        try {
            return json2ObjectMapper.readValue(objectInJson, objectClass);
        } catch (IOException e) {
            LOGGER.error("Error durning parsing object of class '{}' from response '{}'", objectClass, objectInJson, e);
            throw new TechnicalException(e);
        }
    }

}