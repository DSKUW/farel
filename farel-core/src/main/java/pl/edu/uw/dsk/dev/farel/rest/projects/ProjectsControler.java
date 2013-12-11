package pl.edu.uw.dsk.dev.farel.rest.projects;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import pl.edu.uw.dsk.dev.farel.auxilliary.Project;

import com.mongodb.BasicDBObject;

@Controller
@RequestMapping(value = "/projects")
public class ProjectsControler {

    @Autowired
    private MongoOperations mongoOps;
    // TODO-mn: MongoRepository (Basic) ze SpringData zamiast tego

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Project> findAll() {
        List<Project> projects = mongoOps.findAll(Project.class, "project");
        return projects;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> saveAll(@RequestBody List<Project> projectList, UriComponentsBuilder builder) {
        for(Project project : projectList) {
            if(mongoOps.count(new BasicQuery(new BasicDBObject("name", project.getName())), "project") == 0) {
                mongoOps.insert(project, "project");
            }
        }
        UriComponents uri = builder.path("/projects").build();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.toUri());
        return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Project readProjects(@PathVariable("projectId") String id, UriComponentsBuilder builder) {
        return mongoOps.findOne(new BasicQuery(new BasicDBObject("name", id)), Project.class, "project");
    }
}
