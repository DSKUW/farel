package pl.edu.uw.dsk.dev.farel.itest.stories;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.junit.Assert;
import org.springframework.web.client.RestTemplate;

import pl.edu.uw.dsk.dev.farel.exceptions.TechnicalException;
import pl.edu.uw.dsk.dev.farel.itest.TestEnvironmentPropertiesHelper;
import pl.edu.uw.dsk.dev.farel.itest.WebConnector;

public class ProjectDisplayStory extends Steps {

    private RestTemplate restTemplate = new RestTemplate();
    private WebConnector webConnector;
    private ObjectMapper jsonMapper = new ObjectMapper();

    @BeforeStories
    public void setUp() {
        webConnector = new WebConnector();
    }

    @Given("that I define projects within the system")
    public void defineProjects() throws TechnicalException, IOException {
        List<String> projectNames = new ArrayList<String>();
        projectNames.add("Project");
        projectNames.add("Project2");
        projectNames.add("Project3");
        String projectNamesAsString = jsonMapper.writeValueAsString(projectNames);
        restTemplate.postForObject(TestEnvironmentPropertiesHelper.serverUrl() + "rest/test", projectNamesAsString, String.class);
    }

    @When("I access the project list")
    public void accessProjectList() {
        String url = TestEnvironmentPropertiesHelper.serverUrl() + "test.html";
        webConnector.open(url);
    }

    @Then("I see all projects defined in system")
    public void checkForVisibleProjects() {
        // act
        boolean isTextVisible = webConnector.isTextPresent("Project");

        // assert
        Assert.assertTrue(isTextVisible);
    }

    @AfterStories
    public void tearDown() {
        webConnector.quit();
    }
}