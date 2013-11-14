package pl.edu.uw.dsk.dev.farel.itest.stories;

import java.io.IOException;
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
import pl.edu.uw.dsk.dev.farel.itest.WebConnector;

public class ProjectDisplayStory extends Steps {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProjectDisplayStory.class);

    private RestTemplate restTemplate = new RestTemplate();
    private ObjectMapper json2ObjectMapper = new ObjectMapper();
    private List<Project> returnedProjectList;
    private WebConnector webConnector;

    @Given("that projects are defined within the system")
    public void areProjectsDefined() {
        String projectListAsString = restTemplate.getForObject("http://localhost:8080/rest", String.class);
        returnedProjectList = parseObjectInJson(projectListAsString, Project.class);
        Assert.assertNotNull(returnedProjectList);
    }

    private <T> List<T> parseObjectInJson(String objectInJson, Class<T> objectClass) throws TechnicalException {
        try {
            JavaType type = json2ObjectMapper.getTypeFactory().constructCollectionType(List.class, objectClass);
            return json2ObjectMapper.readValue(objectInJson, type);
        } catch (IOException e) {
            LOGGER.error("Error durning parsing object of class '{}' from response '{}'", objectClass, objectInJson, e);
            throw new TechnicalException(e);
        }
    }

    @When("I access the project list")
    public void accessProjectList() {
        webConnector = new WebConnector();
        webConnector.open("http://localhost:8080");
    }

    @Then("I see all projects defined in system")
    public void checkForVisibleProjects() {
        Assert.assertTrue(webConnector.isTextPresent("Test"));
        webConnector.quit();
    }
}