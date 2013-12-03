package pl.edu.uw.dsk.dev.farel.itest.stories;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Named;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.junit.Assert;
import org.springframework.web.client.RestTemplate;

import pl.edu.uw.dsk.dev.farel.auxilliary.Project;
import pl.edu.uw.dsk.dev.farel.itest.browser_config.TestEnvironmentPropertiesHelper;
import pl.edu.uw.dsk.dev.farel.itest.browser_config.WebConnector;

public class AdminAddsProjectStory extends Steps {

    private RestTemplate restTemplate = new RestTemplate();
    private WebConnector webConnector;

    private String name;

    @BeforeStories
    public void setUp() {
        webConnector = new WebConnector();
    }

    @Given("that I access admin panel")
    public void accessAdminPanel() {
        // TODO-mn: Server URL ze springa
        String url = TestEnvironmentPropertiesHelper.serverUrl() + "admin.html";
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
        // TODO-mn: Zamienic response'a na ResponseEntity
        Project response = restTemplate.getForObject(
                        TestEnvironmentPropertiesHelper.serverUrl() + "rest/projects/" + name,
                        Project.class); 
        //assert
        Assert.assertTrue(response.getName().equals(name));
    }

    @AfterStories
    public void tearDown() {
        webConnector.quit();
    }
}