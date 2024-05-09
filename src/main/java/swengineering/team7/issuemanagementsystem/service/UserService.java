package swengineering.team7.issuemanagementsystem.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
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

    //사용자 이름으로 검색
    public Specification<User> searchByusername(String input) {
       return new Specification<User>() {
           @Override
           public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
               query.distinct(true);
               return cb.like(root.get("username"),"%"+input+"%");
           }
       };
    }

    //사용자 권한(admin, PL, dev, tester) 으로 검색
    public Specification<User> searchByRole(String input) {
        return new Specification<User>() {
          @Override
          public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              query.distinct(true);
              return cb.like(root.get("role"),"%"+input+"%");
            }
        };
    }

}
