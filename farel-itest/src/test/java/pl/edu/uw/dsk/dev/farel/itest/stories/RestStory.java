package pl.edu.uw.dsk.dev.farel.itest.stories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.JavaType;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

import pl.edu.uw.dsk.dev.farel.entites.Project;
import pl.edu.uw.dsk.dev.farel.exceptions.TechnicalException;

public class RestStory extends Steps {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestStory.class);

    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper jsonMapper = new ObjectMapper();
    private List<Project> returnedProjectList;

    @Given("that I define projects within the system")
    public void projectsExist() throws IOException {
        List<Project> sourceProjectList = buildProjectList();
        String jsonList = jsonMapper.writeValueAsString(sourceProjectList);
        restTemplate.postForObject("http://localhost:8080/rest", jsonList, String.class);
    }

    private List<Project> buildProjectList() {
        List<Project> sourceList = new ArrayList<Project>();
        sourceList.add(new Project("Test"));
        sourceList.add(new Project("Test1"));
        sourceList.add(new Project("Test2"));
        return sourceList;
    }

    @When("I make REST request")
    public void listIsAccessed() {
        String projectListAsString = restTemplate.getForObject("http://localhost:8080/rest", String.class);
        returnedProjectList = parseObjectInJson(projectListAsString, Project.class);
    }

    private <T> List<T> parseObjectInJson(String objectInJson, Class<T> objectClass) throws TechnicalException {
        try {
            JavaType type = jsonMapper.getTypeFactory().constructCollectionType(List.class, objectClass);
            return jsonMapper.readValue(objectInJson, type);
        } catch (IOException e) {
            LOGGER.error("Error durning parsing object of class '{}' from response '{}'", objectClass, objectInJson, e);
            throw new TechnicalException(e);
        }
    }

    @Then("downloaded names are equal to initial ones")
    public void canSeeAllProjects() {
        Assert.assertEquals(returnedProjectList.get(0).getName(), "Test");
        Assert.assertEquals(returnedProjectList.get(1).getName(), "Test1");
        Assert.assertEquals(returnedProjectList.get(2).getName(), "Test2");
    }
}