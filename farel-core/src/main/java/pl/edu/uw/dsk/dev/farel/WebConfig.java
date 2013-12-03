package pl.edu.uw.dsk.dev.farel;

import java.net.UnknownHostException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.mongodb.MongoClient;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "pl.edu.uw.dsk.dev.farel")
public class WebConfig {

    @Bean
    MongoOperations mongoOperations() throws UnknownHostException {
        return new MongoTemplate(new MongoClient(), "database");
    }
}