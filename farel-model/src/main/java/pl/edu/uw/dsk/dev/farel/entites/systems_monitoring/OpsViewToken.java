package pl.edu.uw.dsk.dev.farel.entites.systems_monitoring;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.joda.time.DateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpsViewToken {

    private DateTime createdAt = new DateTime();
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isExpired() {
        return createdAt.plusHours(1).isBeforeNow();
    }

    public String toString() {
        return token;
    }
}
