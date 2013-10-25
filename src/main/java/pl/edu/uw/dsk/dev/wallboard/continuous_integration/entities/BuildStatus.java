package pl.edu.uw.dsk.dev.wallboard.continuous_integration.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

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