package pl.edu.uw.dsk.dev.farel.rest.continuous_integration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/jenkins")
public class JenkinsControler {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String generalInfo() {
        return "General info about Jenkins";
    }

    @GET
    @Path("{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String projectInfo(@PathParam("projectId") String id) {
        return "Informacje o stanie Jenkins'a dla projektu o id=" + id;
    }
}
