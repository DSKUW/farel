package pl.edu.uw.dsk.dev.farel.entites;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonValue;

public class Service {

    @Id
    private String id;
    private String name;

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

    private ServiceType serviceType;

    private String address;
    private String restEndPoint;

    public Service() { }

    public Service(String name, ServiceType serviceType, String address, String restEndPoint) {
        this.name = name;
        this.serviceType = serviceType;
        this.address = address;
        this.restEndPoint = restEndPoint;
    }

    public Service(String id, String name, ServiceType serviceType, String address, String restEndPoint) {
        this(name, serviceType, address, restEndPoint);
        this.id = id;
    }

    public String getId() {
        return id;
    }

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
