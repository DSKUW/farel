package pl.edu.uw.dsk.dev.farel.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import pl.edu.uw.dsk.dev.farel.entites.Service;

public interface ServiceRepository extends MongoRepository<Service, String> {

    Service findOneServiceByName(String name);

}
