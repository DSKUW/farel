package pl.edu.uw.dsk.dev.farel.entites;

import com.fasterxml.jackson.annotation.JsonValue;

public class Service {
    String name;

    public enum ServiceType {
        BUG_TRACKING("BUG_TRACKING"), CODE_REVIEW("CODE_REVIEW"), CONTINUOUS_INTEGRATION(
                        "CONTINUOUS_INTEGRATION"), SYSTEMS_MONITORING("SYSTEMS_MONITORING");

        String stringValue;

        ServiceType(String stringValue) {
            this.stringValue = stringValue;
        }

        @JsonValue
        String getStringValue() {
            return stringValue;
        }
    }

    ServiceType serviceType;

    String address;
    String restEndPoint;

    public String getName() {
        return name;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public String getAddress() {
        return address;
    }

    public String getRestEndPoint() {
        return restEndPoint;
    }

}
