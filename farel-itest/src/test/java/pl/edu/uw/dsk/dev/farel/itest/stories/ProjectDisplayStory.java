package pl.edu.uw.dsk.dev.farel.itest.stories;

import java.io.IOException;

import org.jbehave.core.annotations.AfterStories;
import org.jbehave.core.annotations.BeforeStories;
import org.jbehave.core.annotations.Given;
import org.jbehave.core.annotations.Then;
import org.jbehave.core.annotations.When;
import org.jbehave.core.steps.Steps;
import org.junit.Assert;
import org.springframework.web.client.RestTemplate;

import pl.edu.uw.dsk.dev.farel.exceptions.TechnicalException;
import pl.edu.uw.dsk.dev.farel.itest.WebConnector;


public class ProjectDisplayStory extends Steps {

    private RestTemplate restTemplate = new RestTemplate();
    private WebConnector webConnector;

    @BeforeStories
    public void setUp() {
        webConnector = new WebConnector();
    }

    @Given("that I define projects within the system")
    public void defineProjects() throws TechnicalException, IOException {
        String jsonString = "{\"list\":[{\"services\":[{\"output\":\"DISK OK - free space: / 21028 MB (37% inode=92%): /dev 10 MB (100% inode=99%): /run 301 MB (99% inode=99%): /run/lock 5 MB (100% inode=99%): /run/shm 793 MB (100% inode=99%):\",\"name\":\"DISKSPACE\"},{\"output\":\"OK - load average: 0.12, 0.14, 0.14\",\"name\":\"LOAD\"},{\"output\":\"jest ok - free[mb]: ram(2231/3016 - 73%), swap(952/952 - 100%)\",\"name\":\"Memory and Swap\"},{\"output\":\"Sys: OK HD: OK HDWrn: OK Proc: OK Srv: OK NMon: OK OK\",\"name\":\"MONIT\"},{\"output\":\"PUPPET OK - state file is 0 minutes old: process running\",\"name\":\"Puppet Agent\"},{\"output\":\"HTTP OK: HTTP/1.1 200 OK - 7837 bytes in 0.024 second response time\",\"name\":\"Status - adres.strony\"}]}]}";
        restTemplate.postForObject("http://localhost:8080/rest/test", jsonString, String.class);
    }

    @When("I access the project list")
    public void accessProjectList() {
        webConnector.open("http://localhost:8080/test.html");
    }

    @Then("I see all projects defined in system")
    public void checkForVisibleProjects() {
        // act
        boolean isTextVisible = webConnector.isTextPresent("name");

        // assert
        Assert.assertTrue(isTextVisible);
    }

    @AfterStories
    public void tearDown() {
        webConnector.quit();
    }
}