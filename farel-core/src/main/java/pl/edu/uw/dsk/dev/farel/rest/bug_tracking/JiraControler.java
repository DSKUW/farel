package pl.edu.uw.dsk.dev.farel.rest.bug_tracking;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/jira")
public class JiraControler {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String generalInfo() {
        return "General info about Jira";
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("{projectId}")
    public String projectInfo(@PathParam("projectId") String id) {
        return "Informacje o stanie Jiry dla projektu o id=" + id;
    }
}
