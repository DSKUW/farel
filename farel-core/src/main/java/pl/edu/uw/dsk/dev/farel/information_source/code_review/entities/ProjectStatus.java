package pl.edu.uw.dsk.dev.farel.information_source.code_review.entities;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ProjectStatus {

    public String name;
    public String description;

    public String toString() {
        return name + ":::" + description;
    }
}