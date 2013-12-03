package pl.edu.uw.dsk.dev.farel.entites.systems_monitoring;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class HostStatus {

    public OpsViewState[] list;

    @Override
    public String toString() {
        StringBuilder appender = new StringBuilder();
        for (int i = 0; i < list[0].services.length; i++) {
            appender.append(list[0].services[i].name).append(":::").append(list[0].services[i].output).append("\n");
        }
        return appender.toString();
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    private static class OpsViewState {

        public OpsViewServices[] services;

        @JsonIgnoreProperties(ignoreUnknown = true)
        static private class OpsViewServices {
            public String output;
            public String name;
        }
    }
}