package pl.edu.uw.dsk.dev.farel.itest;

import java.util.ArrayList;
import java.util.List;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.junit.spring.SpringAnnotatedEmbedderRunner;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;

import pl.edu.uw.dsk.dev.farel.itest.stories.AdminAddsProjectStory;
import pl.edu.uw.dsk.dev.farel.itest.stories.ProjectDisplayStory;

@RunWith(SpringAnnotatedEmbedderRunner.class)
public class FarelIT extends JUnitStories {

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new InstanceStepsFactory(configuration(), storiesClasses());
    }

    private List<Object> storiesClasses() {
        List<Object> storiesClasses = new ArrayList<Object>();
        storiesClasses.add(new ProjectDisplayStory());
        storiesClasses.add(new AdminAddsProjectStory());
        return storiesClasses;
    }

    @Override
    protected List<String> storyPaths() {
        //return new StoryFinder().findPaths("stories", "**/*.story", "");
        List<String> storiesPaths = new ArrayList<String>();
        // TODO-mn: Niech się ładują stories automatycznie
        storiesPaths.add("stories/projectDisplayStory.story");
        storiesPaths.add("stories/adminAddsProjectStory.story");
        return storiesPaths;
    }

    @Override
    public Configuration configuration() {
        return new MostUsefulConfiguration().useStoryReporterBuilder(new StoryReporterBuilder()
                        .withDefaultFormats().withFormats(Format.CONSOLE, Format.TXT));
    }

}
