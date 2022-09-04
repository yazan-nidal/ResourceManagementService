package exp.exalt.ps.repositories;

import exp.exalt.ps.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User,Long> {
   public User findByUsername(String username);
}
