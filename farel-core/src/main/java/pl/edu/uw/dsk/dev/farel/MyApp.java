package pl.edu.uw.dsk.dev.farel;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

import pl.edu.uw.dsk.dev.farel.rest.bug_tracking.JiraControler;
import pl.edu.uw.dsk.dev.farel.rest.calendar.CalendarControler;
import pl.edu.uw.dsk.dev.farel.rest.continuous_integration.JenkinsControler;
import pl.edu.uw.dsk.dev.farel.rest.projects.ProjectsControler;
import pl.edu.uw.dsk.dev.farel.rest.projects.project.ProjectControler;
import pl.edu.uw.dsk.dev.farel.rest.systems_monitoring.OpsViewControler;

public class MyApp extends ResourceConfig {

    public MyApp () {
        register(RequestContextFilter.class);
        register(JiraControler.class);
        register(CalendarControler.class);
        register(JenkinsControler.class);
        register(ProjectControler.class);
        register(ProjectsControler.class);
        register(OpsViewControler.class);
    }
}