package pl.edu.uw.dsk.dev.farel.rest;

import java.io.IOException;
import java.io.PrintWriter;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import pl.edu.uw.dsk.dev.farel.information_source.systems_monitoring.OpsViewManager;
import pl.edu.uw.dsk.dev.farel.information_source.systems_monitoring.entities.HostStatus;
import pl.edu.uw.dsk.dev.farel.utils.LoginInfo;

@Path("/")
public class MyResource {

    private static final String OPSVIEW_BASE_URL = "https://adres.strony/rest/";
    private static final String OPSVIEW_LOGIN = "login";
    private static final String OPSVIEW_PASSWORD = "password";

    private ObjectMapper jsonMapper = new ObjectMapper();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String readProjects() throws JsonGenerationException, JsonMappingException, IOException {
        LoginInfo opsViewLoginInfo = new LoginInfo(OPSVIEW_LOGIN, OPSVIEW_PASSWORD);
        OpsViewManager opsViewManager = new OpsViewManager(OPSVIEW_BASE_URL, opsViewLoginInfo);
        HostStatus hostStatus = opsViewManager.getStatus("jenkins");
        return jsonMapper.writeValueAsString(hostStatus);
    }

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String persistProjects(String jsonString) throws JsonGenerationException, JsonMappingException, IOException {
        PrintWriter writer = new PrintWriter("jsonString.json", "UTF-8");
        writer.print(jsonString);
        writer.close();
        return "1";
    }
}
