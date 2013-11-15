package pl.edu.uw.dsk.dev.farel.entites.bug_tracking;

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