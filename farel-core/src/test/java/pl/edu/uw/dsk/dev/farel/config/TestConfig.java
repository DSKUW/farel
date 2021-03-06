package pl.edu.uw.dsk.dev.farel.config;

import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import pl.edu.uw.dsk.dev.farel.repository.ProjectRepository;
import pl.edu.uw.dsk.dev.farel.repository.ServiceRepository;

@Configuration
public class TestConfig {

    @Bean
    public ProjectRepository projectRepository() {
        return Mockito.mock(ProjectRepository.class);
    }

    @Bean
    public ServiceRepository serviceRepository() {
        return Mockito.mock(ServiceRepository.class);
    }

}
