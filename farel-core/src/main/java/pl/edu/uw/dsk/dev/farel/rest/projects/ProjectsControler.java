package pl.edu.uw.dsk.dev.farel.rest.projects;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

import pl.edu.uw.dsk.dev.farel.entites.Project;
import pl.edu.uw.dsk.dev.farel.repository.ProjectRepository;

@Controller
@RequestMapping(value = "/projects")
public class ProjectsControler {

    @Autowired
    private ProjectRepository projectRepository;

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<Project> findAll() {
        List<Project> projects = projectRepository.findAll();
        return projects;
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<Void> saveAll(@RequestBody List<Project> projectList,
                    UriComponentsBuilder builder) {
        UriComponents uri = builder.path("/projects").build();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.toUri());
        HttpStatus httpStatus = HttpStatus.CONFLICT;

        boolean unique = true;
        for (Project project : projectList) {
            if(null != readProject(project.getName())) {
                unique = false;
            }
        }
        if(unique) {
            for (Project project : projectList) {
                projectRepository.save(project);
            }
            httpStatus = HttpStatus.CREATED;
        }
        return new ResponseEntity<Void>(headers, httpStatus);
    }

    @RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> deleteAll() {
        projectRepository.deleteAll();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Project readProject(@PathVariable("projectId") String id) {
        return projectRepository.findOneProjectByName(id);
    }

    @RequestMapping(value = "/{projectId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<Void> submitProject(@PathVariable("projectId") String id, @RequestBody Project project,
                    UriComponentsBuilder builder) {
        UriComponents uri = builder.path("/projects/" + id).build();
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(uri.toUri());

        if(null == readProject(id)) {
            projectRepository.save(project);
            return new ResponseEntity<Void>(headers, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<Void>(headers, HttpStatus.FORBIDDEN);
        }
    }
}
