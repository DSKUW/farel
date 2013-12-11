package pl.edu.uw.dsk.dev.farel.itest.stories;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.web.client.RestTemplate;

import pl.edu.uw.dsk.dev.farel.auxilliary.Project;
import pl.edu.uw.dsk.dev.farel.exceptions.TechnicalException;
import pl.edu.uw.dsk.dev.farel.itest.ApplicationConfig;
import pl.edu.uw.dsk.dev.farel.itest.browser_config.WebConnector;

@ContextConfiguration(classes = ApplicationConfig.class)
public class ProjectDisplayStory extends Steps {

    @Autowired
    private RestTemplate restTemplate;
    private WebConnector webConnector;

    @Value("${farel.base_url}")
    private String BASE_URL;

    private static final List<Project> PROJECT_LIST = Arrays.asList(new Project(
                    "VALID_PROJECT_NAME_1"), new Project("VALID_PROJECT_NAME_2"), new Project(
                    "VALID_PROJECT_NAME_3"));

    @BeforeStories
    public void setUp() {
        webConnector = new WebConnector();
    }

    @Given("that I define projects within the system")
    public void defineProjects() throws TechnicalException, IOException {
        ParameterizedTypeReference<List<Project>> typeRef = new ParameterizedTypeReference<List<Project>>() {
        };
        ResponseEntity<?> response = restTemplate.exchange(
                        BASE_URL + "rest/projects",
                        HttpMethod.POST, new HttpEntity<List<Project>>(PROJECT_LIST), typeRef);
        Assert.assertTrue(HttpStatus.CREATED.equals(response.getStatusCode()));
    }

    @When("I access the project list")
    public void accessProjectList() {
        webConnector.open(BASE_URL);
    }

    @Then("I see all projects defined in system")
    public void checkForVisibleProjects() {
        // act
        boolean isTextVisible = true;
        for (Project project : PROJECT_LIST) {
            isTextVisible &= webConnector.isTextPresentAtId(project.getName(), "ng-app");
        }
        // assert
        Assert.assertTrue(isTextVisible);
    }

    @AfterStories
    public void tearDown() {
        webConnector.quit();
    }
}