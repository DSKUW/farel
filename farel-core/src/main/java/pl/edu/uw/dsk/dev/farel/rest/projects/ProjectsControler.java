package pl.edu.uw.dsk.dev.farel.rest.projects;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/projects")
public class ProjectsControler {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String readProjects() {
        return "Ogolne informacje o wszystkich projektach";
    }
}
