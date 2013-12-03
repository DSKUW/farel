package pl.edu.uw.dsk.dev.farel.rest.projects;

import static org.springframework.test.web.server.setup.MockMvcBuilders.annotationConfigSetup;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.server.MockMvc;

import pl.edu.uw.dsk.dev.farel.WebConfig;

@Configuration
@ComponentScan(basePackages = "pl.edu.uw.dsk.dev.farel")
public class TestConfig {

    @Bean
    MockMvc mockMvc() {
        return annotationConfigSetup(WebConfig.class).build();
    }

}