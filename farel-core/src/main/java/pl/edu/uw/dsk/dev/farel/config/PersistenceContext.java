package pl.edu.uw.dsk.dev.farel.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import pl.edu.uw.dsk.dev.farel.repository.Repositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackageClasses = Repositories.class)
public class PersistenceContext extends AbstractMongoConfiguration {

    // TODO-ach: Read database from *.properties/System.property/System.env
    @Override
    protected String getDatabaseName() {
        return "database";
    }

    @Override
    public Mongo mongo() throws Exception {
        return new MongoClient();
    }

}
