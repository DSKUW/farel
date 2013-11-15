package pl.edu.uw.dsk.dev.farel.rest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;

@Path("/test")
public class Test {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String readProjects() throws Exception {
        FileInputStream inputFile = new FileInputStream(new File("jsonString.json"));
        String opsViewJson = IOUtils.toString(inputFile, "UTF-8");
        return opsViewJson;
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
