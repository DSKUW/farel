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
import org.jbehave.core.steps.spring.SpringStepsFactory;
import org.junit.runner.RunWith;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

@RunWith(SpringAnnotatedEmbedderRunner.class)
public class FarelIT extends JUnitStories {

    @Override
    public InjectableStepsFactory stepsFactory() {
        return new SpringStepsFactory(configuration(), new AnnotationConfigApplicationContext(ApplicationConfig.class));
        //return new InstanceStepsFactory(configuration(), storiesClasses());
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
