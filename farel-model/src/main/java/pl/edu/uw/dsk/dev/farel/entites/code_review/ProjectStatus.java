package pl.edu.uw.dsk.dev.farel.entites.code_review;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectStatus {

    public String name;
    public String description;

    public String toString() {
        return name + ":::" + description;
    }
}