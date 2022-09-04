package exp.exalt.ps.services;

import exp.exalt.ps.models.User;
import exp.exalt.ps.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

   public User addUpdateUser(User user){
       return userRepository.save(user);
    }

    public  User getUserByUserName(String  username) {
       return userRepository.findByUsername(username);
   }
}
