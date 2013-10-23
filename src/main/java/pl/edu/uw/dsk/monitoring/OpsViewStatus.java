package pl.edu.uw.dsk.monitoring;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class OpsViewStatus {

    static private final String OPSVIEW_API_URL = "https://adres.strony/rest/";
    static private final String OPSVIEW_USERNAME = "login";
    static private final String OPSVIEW_PASSWORD = "password";
    static private final String X_OPSVIEW_USERNAME_HEADER = "X-Opsview-Username";
    static private final String X_OPSVIEW_TOKEN_HEADER = "X-Opsview-Token";

    private OpsViewToken TOKEN;
    private OpsViewResponse responseBean;
    private String opsViewStatus;

    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper mapper = new ObjectMapper();

    OpsViewStatus() throws IOException {
        TOKEN = getToken();
        getStatus();
    }

    private OpsViewToken getToken() throws IOException {
        String tokenInJson = restTemplate.postForObject(OPSVIEW_API_URL + "login", getOpsviewCredentials(),
                        String.class);
        return parseToken(tokenInJson);
    }

    private MultiValueMap<String, String> getOpsviewCredentials() {
        MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
        map.add("username", OPSVIEW_USERNAME);
        map.add("password", OPSVIEW_PASSWORD);
        return map;
    }

    private OpsViewToken parseToken(String tokenInJson) throws IOException {
        OpsViewToken tokenBean = mapper.readValue(tokenInJson, OpsViewToken.class);
        return tokenBean;
    }

    @SuppressWarnings("unused")
    private String getVersion() {
        return restTemplate.getForObject(OPSVIEW_API_URL, String.class);
    }

    private void getStatus() throws JsonParseException, JsonMappingException, IOException {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(X_OPSVIEW_USERNAME_HEADER, OPSVIEW_USERNAME);
        httpHeaders.add(X_OPSVIEW_TOKEN_HEADER, TOKEN.getToken());
        HttpEntity<String> requestBody = new HttpEntity<String>(httpHeaders);
        ResponseEntity<String> response = restTemplate.exchange(OPSVIEW_API_URL + "/status/service?host=jenkins",
                        HttpMethod.GET, requestBody, String.class);
        opsViewStatus = response.getBody();
        responseBean = mapper.readValue(opsViewStatus, OpsViewResponse.class);
    }

    public void displayStatus() {
        System.out.println("OPSVIEW STATUS:");
        for (int i = 0; i < 5; i++) {
            System.out.println(responseBean.list[0].services[i].name + ":::" + responseBean.list[0].services[i].output);
        }
    }
}