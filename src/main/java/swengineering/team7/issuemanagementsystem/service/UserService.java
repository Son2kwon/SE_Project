package swengineering.team7.issuemanagementsystem.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import swengineering.team7.issuemanagementsystem.DTO.UserInformationDTO;
import swengineering.team7.issuemanagementsystem.entity.User;
import swengineering.team7.issuemanagementsystem.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    private final UserRepository userRepository;

   @Autowired
    public UserService(UserRepository userRepository) {
       this.userRepository=userRepository;
   }

   //회원가입
    public void create(String username, String password, String role) {
       User user = new User();
       user.setUsername(username);
       user.setPassword(password);
       user.setRole(role);
       this.userRepository.save(user);
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // 사용자 계정 정보 수정
    public void modifyUsername(User user, String newname) {
       user.setUsername(newname);
       this.userRepository.save(user);
    }

    public void modifyUserpassword(User user, String newpassword) {
       user.setPassword(newpassword);
       this.userRepository.save(user);
    }

    public void modifyUserrole(User user, String newrole) {
       user.setRole(newrole);
       this.userRepository.save(user);
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //이름으로 검색된 사용자들 DTO 반환
    public List<UserInformationDTO> searchByUsername(String input) {
        Specification<User> spec = searchname(input);
        List<User> users = userRepository.findAll(spec);

        List<UserInformationDTO> userInformationDTOS = new ArrayList<>();
        for (User user : users) {
            userInformationDTOS.add(UserInformationDTO.from(user));
        }

        return userInformationDTOS;
    }
    //사용자 이름으로 검색 하는 메소드
    public Specification<User> searchname(String input) {
       return new Specification<User>() {
           @Override
           public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
               query.distinct(true);
               return cb.like(root.get("username"),"%"+input+"%");
           }
       };
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //사용자 권한으로 검색된 리스트 DTO 반환
    public List<UserInformationDTO> searchByUserrole(String input) {
    Specification<User> spec = searchrole(input);
    List<User> users = userRepository.findAll(spec);

    List<UserInformationDTO> userInformationDTOS = new ArrayList<>();
    for (User user : users) {
        userInformationDTOS.add(UserInformationDTO.from(user));
    }

    return userInformationDTOS;
}
    //사용자 권한(admin, PL, dev, tester) 으로 검색
    public Specification<User> searchrole(String input) {
        return new Specification<User>() {
          @Override
          public Predicate toPredicate(Root<User> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
              query.distinct(true);
              return cb.like(root.get("role"),"%"+input+"%");
            }
        };
    }

}

///// add comment : 해당 프로젝트에 속한 사용자만 가능