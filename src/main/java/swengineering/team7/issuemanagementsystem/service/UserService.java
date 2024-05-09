package swengineering.team7.issuemanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swengineering.team7.issuemanagementsystem.entity.User;
import swengineering.team7.issuemanagementsystem.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

   @Autowired
    public UserService(UserRepository userRepository) {
       this.userRepository=userRepository;
   }

    public User create(String username, String password, String contract) {
       User user = new User();
       user.setUsername(username);
       user.setPassword(password);
       user.setRole(contract);
       this.userRepository.save(user);
       return user;
    }

}
