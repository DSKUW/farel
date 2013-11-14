package pl.edu.uw.dsk.monitoring;

import java.util.Arrays;
import java.util.List;

import org.jbehave.core.embedder.Embedder;

public class SimpleJBehave {
    private static Embedder embedder = new Embedder();
    private static List<String> storyPaths = Arrays.asList("story.story");

    public static void main(String[] args) {
        embedder.candidateSteps().add(new TestOpsViewManager());
        embedder.runStoriesAsPaths(storyPaths);
    }
}