package pl.edu.uw.dsk.monitoring;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;

public class StatusMonitor {
    public static void main(String[] args) throws JsonParseException, JsonMappingException, IOException {
        OpsViewStatus opsViewStatus = new OpsViewStatus();
        opsViewStatus.displayStatus();
        System.out.println();
        JenkinsStatus jenkinsStatus = new JenkinsStatus();
        jenkinsStatus.displayStatus();
        System.out.println();
        CodereviewStatus codereviewStatus = new CodereviewStatus();
        codereviewStatus.displayStatus();
    }
}