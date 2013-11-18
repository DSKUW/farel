package pl.edu.uw.dsk.dev.farel.rest.calendar;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/calendar")
public class CalendarControler {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String readProjects(@PathParam("projectId") String id) {
        return "Calendar";
    }
}
