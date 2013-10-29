package pl.edu.uw.dsk.dev.farel;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pl.edu.uw.dsk.dev.farel.exceptions.TechnicalException;
import pl.edu.uw.dsk.dev.farel.information_source.bug_tracking.JiraManager;
import pl.edu.uw.dsk.dev.farel.information_source.code_review.CodeReviewManager;
import pl.edu.uw.dsk.dev.farel.information_source.continuous_integration.JenkinsManager;
import pl.edu.uw.dsk.dev.farel.information_source.continuous_integration.entities.BuildStatus;
import pl.edu.uw.dsk.dev.farel.information_source.systems_monitoring.OpsViewManager;
import pl.edu.uw.dsk.dev.farel.information_source.systems_monitoring.entities.HostStatus;
import pl.edu.uw.dsk.dev.farel.utils.LoginInfo;

public class StatusMonitor {

    private static final Logger LOGGER = LoggerFactory.getLogger(StatusMonitor.class);

    private static final String OPSVIEW_BASE_URL = "https://adres.strony/rest/";
    private static final String OPSVIEW_LOGIN = "login";
    private static final String OPSVIEW_PASSWORD = "password";

    private static final String JENKINS_BASE_URL = "https://adres.strony/";
    private static final String JENKINS_LOGIN = "login";
    private static final String JENKINS_PASSWORD = "password";

    private static final String CODEREVIEW_BASE_URL = "https://adres.strony/";
    private static final String CODEREVIEW_LOGIN = "login";
    private static final String CODEREVIEW_PASSWORD = "password";

    private static final String JIRA_BASE_URL = "https://adres.strony/rest/";
    private static final String JIRA_LOGIN = "login";
    private static final String JIRA_PASSWORD = "password";

    public static void main(String[] args) throws TechnicalException, IOException {
        opsView();
        jenkins();
        codeReview();
        jira();
    }

    private static void opsView() throws IOException {
        LoginInfo opsViewLoginInfo = new LoginInfo(OPSVIEW_LOGIN, OPSVIEW_PASSWORD);
        String hostName = "jenkins";
        OpsViewManager opsViewManager = new OpsViewManager(OPSVIEW_BASE_URL, opsViewLoginInfo);
        HostStatus hostStatus = opsViewManager.getStatus(hostName);
        LOGGER.info("OPSVIEW STATUS:\n" + hostStatus + "\n");
    }

    private static void jenkins() throws IOException {
        LoginInfo jenkinsLoginInfo = new LoginInfo(JENKINS_LOGIN, JENKINS_PASSWORD);
        String projectName = "probad-nightly";
        JenkinsManager jenkinsManager = new JenkinsManager(JENKINS_BASE_URL, jenkinsLoginInfo);
        BuildStatus projectStatus = jenkinsManager.getStatus(projectName);
        LOGGER.info("JENKINS STATUS:\n" + projectStatus + "\n");
    }

    private static void codeReview() throws IOException {
        LoginInfo codeReviewLoginInfo = new LoginInfo(CODEREVIEW_LOGIN, CODEREVIEW_PASSWORD);
        String projectName = "probad";
        CodeReviewManager codeReviewManager = new CodeReviewManager(CODEREVIEW_BASE_URL, codeReviewLoginInfo);
        LOGGER.info("CODEREVIEW STATUS:\n" + codeReviewManager.getStatus(projectName));
    }

    private static void jira() throws IOException {
        LoginInfo jiraLoginInfo = new LoginInfo(JIRA_LOGIN, JIRA_PASSWORD);
        String ticketName = "PROBAD-13";
        JiraManager jiraManager = new JiraManager(JIRA_BASE_URL, jiraLoginInfo);
        LOGGER.info("CODEREVIEW STATUS:\n" + jiraManager.getStatus(ticketName));
    }
}