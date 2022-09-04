package exp.exalt.ps.repositories;

import exp.exalt.ps.models.Server;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServerRepository extends MongoRepository<Server,Long> {
    List<Server> findAllByState(long state);
}
