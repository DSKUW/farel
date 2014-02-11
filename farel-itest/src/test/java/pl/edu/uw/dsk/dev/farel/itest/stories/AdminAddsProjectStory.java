package pl.edu.uw.dsk.dev.farel.itest.stories;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import pl.edu.uw.dsk.dev.farel.entites.Project;
import pl.edu.uw.dsk.dev.farel.itest.browser_config.WebConnector;

@Component
public class AdminAddsProjectStory extends Steps {

    @Autowired
    private RestTemplate restTemplate;
    private WebConnector webConnector;

    @Value("${farel.base_url}")
    private String BASE_URL;

    private String name;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @BeforeStories
    public void setUp() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        restTemplate.exchange(BASE_URL + "rest/projects", HttpMethod.DELETE,
                        new HttpEntity(headers), ResponseEntity.class);
        webConnector = new WebConnector();
    }

    @Given("that I access admin panel")
    public void accessAdminPanel() {
        String url = BASE_URL + "admin_add.html";
        webConnector.open(url);
    }

    @When("I set <name> as a name for the project")
    public void setProjectName(@Named("name") String name) throws InterruptedException {
        this.name = name;
        webConnector.fill("nameField", name);
    }

    @When("I click the Add button")
    public void clickAddButton() {
        webConnector.clickAndWait("addProject");
    }

    @Then("project will be added to the system")
    public void callRestToAddToDb() {
        // act
        ParameterizedTypeReference<Project> typeRef = new ParameterizedTypeReference<Project>() {
        };
        ResponseEntity<Project> response = restTemplate.exchange(
                        BASE_URL + "rest/projects/" + name, HttpMethod.GET, null, typeRef);
        // assert
        Assert.assertTrue(response.getBody().getName().equals(name));
    }

    @AfterStories
    public void tearDown() {
        webConnector.quit();
    }
}
