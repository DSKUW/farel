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

import pl.edu.uw.dsk.dev.farel.auxilliary.Project;
import pl.edu.uw.dsk.dev.farel.exceptions.TechnicalException;
import pl.edu.uw.dsk.dev.farel.itest.browser_config.TestEnvironmentPropertiesHelper;
import pl.edu.uw.dsk.dev.farel.itest.browser_config.WebConnector;

public class ProjectDisplayStory extends Steps {

    private RestTemplate restTemplate = new RestTemplate();
    private WebConnector webConnector;
    private ObjectMapper jsonMapper = new ObjectMapper();

    private Project project1 = new Project("VALID_PROJECT_NAME_1");
    private Project project2 = new Project("VALID_PROJECT_NAME_2");
    private Project project3 = new Project("VALID_PROJECT_NAME_3");

    @BeforeStories
    public void setUp() {
        webConnector = new WebConnector();
    }

    @Given("that I define projects within the system")
    public void defineProjects() throws TechnicalException, IOException {
        List<Project> projectNames = new ArrayList<Project>();
        projectNames.add(project1);
        projectNames.add(project2);
        projectNames.add(project3);
        String projectNamesAsString = jsonMapper.writeValueAsString(projectNames);
        restTemplate.postForObject(TestEnvironmentPropertiesHelper.serverUrl() + "rest/projects", projectNamesAsString, String.class);
    }

    @When("I access the project list")
    public void accessProjectList() {
        String url = TestEnvironmentPropertiesHelper.serverUrl();
        webConnector.open(url);
    }

    @Then("I see all projects defined in system")
    public void checkForVisibleProjects() {
        // act
        boolean isTextVisible = webConnector.isTextPresentAtId(project1.getName(), "ng-app");
        isTextVisible &= webConnector.isTextPresentAtId(project2.getName(), "ng-app");
        isTextVisible &= webConnector.isTextPresentAtId(project3.getName(), "ng-app");
        
        // assert
        Assert.assertTrue(isTextVisible);
    }

    @AfterStories
    public void tearDown() {
        webConnector.quit();
    }
}