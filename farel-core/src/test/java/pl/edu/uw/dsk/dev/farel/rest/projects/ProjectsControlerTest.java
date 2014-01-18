package pl.edu.uw.dsk.dev.farel.rest.projects;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
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
    public void whenGettingAllProjectsAllOfThemShoulBeReturned() throws Exception {
        // arrange
        List<Project> projectList = new ArrayList<Project>();
        projectList.add(new Project("1", "test1"));
        projectList.add(new Project("2", "test2"));
        Mockito.when(projectRepositoryMock.findAll()).thenReturn(projectList);
        // act
        ResultActions result = mockMvc.perform(get("/projects"));

        // assert
        result.andExpect(status().isOk())
                    .andExpect(content().string(
                     "[{\"id\":\"1\",\"name\":\"test1\"},{\"id\":\"2\",\"name\":\"test2\"}]"));
    }

    @Test
    public void whenPostingListOfNonExistingProjectsAllOfThemShouldBeAdded() throws Exception {
        // arrange
        Mockito.when(projectRepositoryMock.findOneProjectByName("test1")).thenReturn(null);
        Mockito.when(projectRepositoryMock.findOneProjectByName("test2")).thenReturn(null);

        // act
        ResultActions result = mockMvc.perform(post("/projects/addall")
                               .contentType(MediaType.APPLICATION_JSON)
                               .content("[{\"id\":1,\"name\":\"test1\"},{\"id\":2,\"name\":\"test2\"}]"
                               .getBytes()));

        // assert
        result.andExpect(status().isCreated()).andExpect(header().string("Location", "http://localhost/projects"));

        Mockito.verify(projectRepositoryMock).save(new Project("1", "test1"));
        Mockito.verify(projectRepositoryMock).save(new Project("2", "test2"));
    }

    @Test
    public void whenPostingListOfAlreadyExistingProjectsAllOfThemShouldBeDiscarded()
                    throws Exception {
        // arrange
        Mockito.when(projectRepositoryMock.findOneProjectByName("test1")).thenReturn(new Project("1", "test1"));
        Mockito.when(projectRepositoryMock.findOneProjectByName("test2")).thenReturn(new Project("2", "test2"));

        // act
        ResultActions result = mockMvc.perform(post("/projects/addall")
                               .contentType(MediaType.APPLICATION_JSON)
                               .content("[{\"id\":1,\"name\":\"test1\"},{\"id\":2,\"name\":\"test2\"}]"
                               .getBytes()));

        // assert
        result.andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturnProperProjectWhenItsUrlIsAccessed() throws Exception {
        // arrange
        Mockito.when(projectRepositoryMock.findOneProjectByName("test1")).thenReturn(new Project("1", "test1"));

        // act
        ResultActions result = mockMvc.perform(get("/projects/test1"));

        // assert
        result.andExpect(status().isOk()).andExpect(content().string("{\"id\":\"1\",\"name\":\"test1\"}"));
    }

    @Test
    public void whenPostingOneProjectItShouldBeAddedIfItsNameIsNotInUseAlready() throws Exception {
        // arrange
        Mockito.when(projectRepositoryMock.findOneProjectByName("test1")).thenReturn(null);

        // act
        ResultActions result = mockMvc.perform(post("/projects").
                        contentType(MediaType.APPLICATION_JSON).
                        content("{\"id\":1,\"name\":\"test1\"}".
                        getBytes()));

        // assert
        result.andExpect(status().isCreated()).andExpect(header().string("Location", "http://localhost/projects/test1"));

        Mockito.verify(projectRepositoryMock).save(new Project("1", "test1"));
    }

    @Test
    public void whenPostingOneProjectItShouldNotBeAddedIfItsNameIsInUseAlready() throws Exception {
        // arrange
        Mockito.when(projectRepositoryMock.findOneProjectByName("test1")).thenReturn(new Project("1", "test1"));

        // act
        ResultActions result = mockMvc.perform(post("/projects").
                        contentType(MediaType.APPLICATION_JSON).
                        content("{\"id\":1,\"name\":\"test1\"}".
                        getBytes()));

        // assert
        result.andExpect(status().isForbidden());
    }
}
