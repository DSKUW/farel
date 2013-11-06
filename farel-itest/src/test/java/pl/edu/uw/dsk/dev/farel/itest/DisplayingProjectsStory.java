package pl.edu.uw.dsk.dev.farel.itest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;

import org.codehaus.jackson.map.ObjectMapper;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import pl.edu.uw.dsk.dev.farel.exceptions.TechnicalException;
import pl.edu.uw.dsk.dev.farel.itest.entities.Project;
import pl.edu.uw.dsk.dev.farel.itest.entities.ProjectBean;

public class DisplayingProjectsStory extends Steps {

    private static final Logger LOGGER = LoggerFactory.getLogger(DisplayingProjectsStory.class);
    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper json2ObjectMapper = new ObjectMapper();
    private List<Project> list;

    @Given("that I defined projects within the system")
    public void projectsExist() {
        List<Project> list = buildProjectList();
        restTemplate.postForObject("http://localhost/rest/projects", list, String.class);
    }

    private List<Project> buildProjectList() {
        List<Project> list = new ArrayList<Project>();
        list.add(new Project("Test"));
        list.add(new Project("Test2"));
        list.add(new Project("Test3"));
        return list;
    }

    @When("I access the project list")
    public void listIsAccessed() {
        String projectListAsString = restTemplate.getForObject("http://localhost/rest/projects", String.class);
        list = parseObjectInJson(projectListAsString, ProjectBean.class).toList();
    }

    private <T> T parseObjectInJson(String objectInJson, Class<T> objectClass) throws TechnicalException {
        try {
            return json2ObjectMapper.readValue(objectInJson, objectClass);
        } catch (IOException e) {
            LOGGER.error("Error durning parsing object of class '{}' from response '{}'", objectClass, objectInJson, e);
            throw new TechnicalException(e);
        }
    }

    @Then("I see all projects defined in system")
    public void canSeeAllProjects() {
        Assert.assertEquals(list.get(0).getName(), "Test");
        Assert.assertEquals(list.get(1).getName(), "Test2");
        Assert.assertEquals(list.get(2).getName(), "Test3");
    }
}