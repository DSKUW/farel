package pl.edu.uw.dsk.dev.farel.entites.bug_tracking;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TicketEntity {
    
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

    public Fields fields;
    
    public String toString() {
        return fields.summary.value + ":::" + fields.description.value;
    }
}