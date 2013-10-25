package pl.edu.uw.dsk.dev.wallboard.bug_tracking;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import pl.edu.uw.dsk.dev.wallboard.LoginInfo;
import pl.edu.uw.dsk.dev.wallboard.bug_tracking.entities.JiraSession;
import pl.edu.uw.dsk.dev.wallboard.exceptions.TechnicalException;

public class JiraManager {

        private static final Logger LOGGER = LoggerFactory.getLogger(JiraManager.class);

        private final String baseUrl;
        private String username;
        private String password;

        private JiraSession token;
        private HttpEntity<String> authorizedRequest;
        //private JiraStatus jiraStatus;

        private RestTemplate restTemplate = new RestTemplate();
        private ObjectMapper json2ObjectMapper = new ObjectMapper();

        public JiraManager(String baseUrl, LoginInfo loginInfo) {
            this.username = loginInfo.getUsername();
            this.password = loginInfo.getPassword();
            this.baseUrl = baseUrl;
        }

        public void getStatus(String ticketName) {
            //zmienic zeby zwracalo beana (ktorego najpierw musze stworzyc)
            //w ktorym bedzie metoda toString ktora bedzie zwracac info
            obtainToken();
            createAuthorizedRequest();
            ResponseEntity<String> response = restTemplate.exchange(baseUrl + "api/2.0.alpha1/issue/" + ticketName,
                            HttpMethod.GET, authorizedRequest, String.class);
            System.out.println(response.getBody());
            //hostStatus = parseObjectInJson(response.getBody(), HostStatus.class);
            //return hostStatus;
        }

        private void createAuthorizedRequest() {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("JSESSIONID", token.getSessionId());
            authorizedRequest = new HttpEntity<String>(httpHeaders);
        }

        private JiraSession obtainToken() {
            String tokenInJson = restTemplate.postForObject(baseUrl + "auth/1/session", getOpsviewCredentials(), String.class);
            token = parseObjectInJson(tokenInJson, JiraSession.class);
            return token;
        }

        private HashMap<String, String> getOpsviewCredentials() {
            HashMap<String, String> map = new HashMap<String, String>();
            map.put("username", username);
            map.put("password", password);
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