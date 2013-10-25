package pl.edu.uw.dsk.dev.wallboard.bug_tracking.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraSession {
    
    public Session session;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Session {
        String value;
    }
    
    public String getSessionId() {
        return session.value;
    }
}
