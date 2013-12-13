package pl.edu.uw.dsk.dev.farel.rest.projects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import pl.edu.uw.dsk.dev.farel.config.TestConfig;
import pl.edu.uw.dsk.dev.farel.config.WebAppContext;
import pl.edu.uw.dsk.dev.farel.entites.Project;
import pl.edu.uw.dsk.dev.farel.repository.ProjectRepository;

@WebAppConfiguration
@ContextConfiguration(classes = { TestConfig.class, WebAppContext.class })
public class ProjectsControlerTest extends AbstractTestNGSpringContextTests {

    @Autowired
    private WebApplicationContext webAppCtx;
    @Autowired
    private ProjectRepository projectRepositoryMock;

    private MockMvc mockMvc;

    @BeforeMethod
    protected void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webAppCtx).build();

        Mockito.reset(projectRepositoryMock);
    }

    @Test
    public void find() throws Exception {
        // arrange
        Project project = new Project("1", "farel");
        Mockito.when(projectRepositoryMock.findOneProjectByName(project.getId())).thenReturn(
                        project);

        // act
        ResultActions result = mockMvc.perform(get("/projects/1"));

        // assert
        result.andExpect(status().isOk()).andExpect(
                        content().string("{\"id\":\"1\",\"name\":\"farel\"}"));
    }

    @Test
    public void saveAll() throws Exception {
        // act
        ResultActions result = mockMvc.perform(post("/projects").contentType(
                        MediaType.APPLICATION_JSON).content(
                        "[{\"id\":null,\"name\":\"a\"},{\"id\":null,\"name\":\"b\"}]".getBytes()));

        // assert
        result.andExpect(status().isCreated()).andExpect(
                        header().string("Location", "http://localhost/projects"));

        Mockito.verify(projectRepositoryMock).save(new Project("a"));
        Mockito.verify(projectRepositoryMock).save(new Project("b"));
    }

    @Test
    public void noSave() throws Exception {
        // arrange
        Mockito.when(projectRepositoryMock.findOneProjectByName("a")).thenReturn(new Project("a"));

        // act
        ResultActions result = mockMvc.perform(post("/projects/a").contentType(
                        MediaType.APPLICATION_JSON).content(
                        "{\"id\":null,\"name\":\"a\"}".getBytes()));

        // assert
        result.andExpect(status().isForbidden())
              .andExpect(header().string("Location", "http://localhost/projects/a"));
    }

    @Test
    public void findAll() throws Exception {
        // arrange
        List<Project> projects = Arrays.asList(new Project("a"), new Project("b"));
        Mockito.when(projectRepositoryMock.findAll()).thenReturn(projects);

        // act
        ResultActions result = mockMvc.perform(get("/projects"));
        // assert
        result.andExpect(status().isOk())
                        .andExpect(content()
                                        .string("[{\"id\":null,\"name\":\"a\"},{\"id\":null,\"name\":\"b\"}]"));
    }
}
