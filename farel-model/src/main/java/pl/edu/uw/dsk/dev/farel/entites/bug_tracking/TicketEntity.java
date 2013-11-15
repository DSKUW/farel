package pl.edu.uw.dsk.dev.farel.entites.bug_tracking;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketEntity {
    
    public Fields fields;
    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class Fields {
        public Summary summary;
        public Description description;
        @JsonIgnoreProperties(ignoreUnknown = true)
        private static class Summary {
            public String value;
        }
        @JsonIgnoreProperties(ignoreUnknown = true)
        private static class Description {
            public String value;
        }
    }
    public String toString() {
        return fields.summary.value + ":::" + fields.description.value;
    }
}