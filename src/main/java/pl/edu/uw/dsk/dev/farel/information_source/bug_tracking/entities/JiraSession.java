package pl.edu.uw.dsk.dev.farel.information_source.bug_tracking.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class JiraSession {
    
    public Session session;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Session {
        public String value;
    }
    
    public String getSessionId() {
        return session.value;
    }
}