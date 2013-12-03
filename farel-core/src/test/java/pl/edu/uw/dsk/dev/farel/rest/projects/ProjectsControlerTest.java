package pl.edu.uw.dsk.dev.farel.rest.projects;

import static org.springframework.test.web.server.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.server.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.server.MockMvc;
import org.testng.annotations.Test;

@WebAppConfiguration
@ContextConfiguration(classes = TestConfig.class)
public class ProjectsControlerTest extends AbstractTestNGSpringContextTests{

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void save() throws Exception {
        mockMvc
            .perform(get("/projects/1"))
            .andExpect(status().isOk())
            .andExpect(content().string("{\"id\":null,\"name\":\"1\"}"));
    }

//    @Test
//    public void saveAll() throws Exception {
//        //UriComponentBuilder
//        //UriComponent
//        //Uri
//        mockMvc
//            .perform(
//                    post("/projects")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .body("[{\"id\":null,\"name\":\"a\"},{\"id\":null,\"name\":\"b\"}]".getBytes()))
//            .andExpect(status().isCreated())
//            .andExpect(header().string("Location", "http://localhost/projects"));
//    }
//
//    @Test
//    public void findAll() throws Exception {
//        mockMvc
//            .perform(get("/projects"))
//            .andExpect(status().isOk())
//            .andExpect(content().string("[{\"id\":null,\"name\":\"a\"},{\"id\":null,\"name\":\"b\"}]"));
//    }
}
