package pl.edu.uw.dsk.dev.farel.information_source.bug_tracking;

import java.io.IOException;
import java.util.HashMap;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import pl.edu.uw.dsk.dev.farel.exceptions.TechnicalException;
import pl.edu.uw.dsk.dev.farel.information_source.bug_tracking.entities.JiraSession;
import pl.edu.uw.dsk.dev.farel.information_source.bug_tracking.entities.TicketEntity;
import pl.edu.uw.dsk.dev.farel.utils.LoginInfo;

public class JiraManager {

        private static final Logger LOGGER = LoggerFactory.getLogger(JiraManager.class);

        private final String baseUrl;
        private String username;
        private String password;

        private JiraSession sessionID;
        private HttpEntity<String> authorizedRequest;
        private TicketEntity ticketEntity;

        private RestTemplate restTemplate = new RestTemplate();
        private ObjectMapper json2ObjectMapper = new ObjectMapper();

        public JiraManager(String baseUrl, LoginInfo loginInfo) {
            this.username = loginInfo.getUsername();
            this.password = loginInfo.getPassword();
            this.baseUrl = baseUrl;
        }

        public String getStatus(String ticketName) {
            obtainToken();
            createAuthorizedRequest();
            ResponseEntity<String> response = restTemplate.exchange(baseUrl + "api/2.0.alpha1/issue/" + ticketName,
                            HttpMethod.GET, authorizedRequest, String.class);
            ticketEntity = parseObjectInJson(response.getBody(), TicketEntity.class);
            return ticketEntity.toString();
        }

        private void createAuthorizedRequest() {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Cookie", "JSESSIONID=" + sessionID.getSessionId());
            authorizedRequest = new HttpEntity<String>(httpHeaders);
        }

        private JiraSession obtainToken() {
            String tokenInJson = restTemplate.postForObject(baseUrl + "auth/1/session", getCredentials(), String.class);
            sessionID = parseObjectInJson(tokenInJson, JiraSession.class);
            return sessionID;
        }

        private HashMap<String, String> getCredentials() {
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