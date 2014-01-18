package pl.edu.uw.dsk.dev.farel.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.edu.uw.dsk.dev.farel.entites.Project;

public interface ProjectRepository extends MongoRepository<Project, String> {

    Project findOneProjectByName(String name);

}
