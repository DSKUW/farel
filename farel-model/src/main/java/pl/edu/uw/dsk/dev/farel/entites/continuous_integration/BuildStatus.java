package pl.edu.uw.dsk.dev.farel.entites.continuous_integration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BuildStatus {

    private String result;

    public String getResult() {
        return result;
    }
    public void setResult(String result) {
        this.result = result;
    }
    public String toString() {
        return result;
    }
}