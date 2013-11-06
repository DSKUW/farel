package pl.edu.uw.dsk.dev.farel;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MultivaluedMap;

/** Example resource class hosted at the URI path "/myresource"
 */
@Path("/myresource")
public class MyResource {
    
    /** Method processing HTTP GET requests, producing "text/plain" MIME media
     * type.
     * @return String that will be send back as a response of type "text/plain".
     */
    @GET 
    @Produces("text/plain")
    public String getIt() {
        return "Hi there!";
    }
    @POST
    @Consumes("application/x-www-form-urlencoded")
    @Produces("text/plain")//application/json
    public String persistProjects(MultivaluedMap<String,String> projectsToPersist) {
        return projectsToPersist.getFirst("1");
    }
}