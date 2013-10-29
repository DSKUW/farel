package pl.edu.uw.dsk.dev.wallboard.bug_tracking.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketEntity {
    
    public Fields fields;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Fields {
        Summary summary;
        Description description;
        @JsonIgnoreProperties(ignoreUnknown = true)
        private static class Summary {
            String value;
        }
        @JsonIgnoreProperties(ignoreUnknown = true)
        private static class Description {
            String value;
        }
    }
    public String toString() {
        return fields.summary.value + ":::" + fields.description.value;
    }
}