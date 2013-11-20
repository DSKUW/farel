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
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;

import pl.edu.uw.dsk.dev.farel.auxilliary.Project;

import com.mongodb.MongoClient;

@Path("/projects")
public class ProjectsControler {

    private ObjectMapper jsonMapper = new ObjectMapper();
    private MongoOperations mongoOps = new MongoTemplate(new MongoClient(), "database");

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
