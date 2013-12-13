package pl.edu.uw.dsk.dev.farel.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.edu.uw.dsk.dev.farel.entites.Project;

public interface ProjectRepository extends MongoRepository<Project, String> {

    List<Project> findAll();
    Project findOneProjectByName(String name);

}
