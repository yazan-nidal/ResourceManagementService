package exp.exalt.ps.repositories;

import exp.exalt.ps.models.STime;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface STimeRepository extends MongoRepository<STime,Long> {
}
