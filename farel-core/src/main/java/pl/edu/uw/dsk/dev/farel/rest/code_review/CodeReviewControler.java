package pl.edu.uw.dsk.dev.farel.rest.code_review;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/codereview")
public class CodeReviewControler {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String generalInfo() {
        return "General info about CodeReview";
    }

    @GET
    @Path("{projectId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String projectInfo(@PathParam("projectId") String id) {
        return "Informacje o stanie CodeReview dla projektu o id=" + id;
    }
}
