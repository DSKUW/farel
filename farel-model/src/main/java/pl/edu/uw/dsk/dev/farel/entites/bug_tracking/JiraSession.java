package pl.edu.uw.dsk.dev.farel.entites.bug_tracking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraSession {
    
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Session {
        public String value;
    }
    
    public Session session;

    public String getSessionId() {
        return session.value;
    }
}
