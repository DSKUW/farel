package pl.edu.uw.dsk.dev.farel.rest.projects;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.type.TypeFactory;
import org.codehaus.jackson.type.JavaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;

import pl.edu.uw.dsk.dev.farel.auxilliary.Project;

@Path("/projects")
public class ProjectsControler {

    @Autowired
    private ObjectMapper jsonMapper;
    @Autowired
    private MongoOperations mongoOps;

    public ProjectsControler() throws UnknownHostException {
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public String findAll() throws Exception {
        List<Project> projectList = mongoOps.findAll(Project.class);
        return jsonMapper.writeValueAsString(projectList);
    }

    @POST
    @Produces(MediaType.TEXT_PLAIN)
    public String persistProjects(String hostStatusString) throws JsonGenerationException, JsonMappingException, IOException {
        JavaType projectListType = TypeFactory.defaultInstance().constructCollectionType(List.class, Project.class);
        List<Project> projectList = jsonMapper.readValue(hostStatusString, projectListType);
        mongoOps.insertAll(projectList);
        return "1";
    }
}
