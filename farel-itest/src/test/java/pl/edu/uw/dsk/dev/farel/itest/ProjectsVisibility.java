package pl.edu.uw.dsk.dev.farel.itest;

import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.testng.Assert;

public class ProjectsVisibility extends Steps {

    @Given("that projects are defined within the $system")
    public void projectsExist(String system) {
        Assert.assertTrue(system.equals("system"));
        /*
         * if(projects.amount < 1) { throw new
         * RuntimeException("projects amount is equal" + projects.amount); }
         */
    }

    @When("I access the project list")
    public void listIsAccessed() {
        // projects.display();
    }

    @Then("I see all projects defined in system")
    public void canSeeAllProjects() {
        // assertThat.amountOfVisibleProjectsIsEqualToAmountOfAllTheProjects();
    }
}