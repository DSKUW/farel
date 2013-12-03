package pl.edu.uw.dsk.dev.farel.entites.systems_monitoring;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpsViewToken {

    private Date createdAt = new Date();
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public boolean isExpired() {
        return createdAt.before(new Date(new Date().getTime() - 3600000));
    }

    public String toString() {
        return token;
    }
}
