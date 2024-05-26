package swengineering.team7.issuemanagementsystem.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import swengineering.team7.issuemanagementsystem.DTO.IssueDTO;
import swengineering.team7.issuemanagementsystem.DTO.UserInformationDTO;
import swengineering.team7.issuemanagementsystem.entity.Project;
import swengineering.team7.issuemanagementsystem.entity.User;
import swengineering.team7.issuemanagementsystem.exception.NoPermission;
import swengineering.team7.issuemanagementsystem.repository.UserRepository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;


@Service
public class UserService {
    private final UserRepository userRepository;

   @Autowired
    public UserService(UserRepository userRepository, EntityManager entityManager) {
       this.userRepository=userRepository;
   }

   //회원가입
    public void createUser(String id, String password, String username,  String contract) {
       User user = new User(id, username,password,contract);
       /*user.setId(id);
       user.setUsername(username);
       user.setPassword(password);
       user.setContract(contract);*/
       this.userRepository.save(user);
    }
    public boolean login(UserInformationDTO userInformationDTO){
       User user = userRepository.findById(userInformationDTO.getId()).orElse(null);
        return user != null && user.getPassword().equals(userInformationDTO.getPassword());
    }
/////////////////////////////////////////////////////////////////////////////////////////////////////////////

    // 사용자 계정 정보 수정
    public void modifyUsername(UserInformationDTO userDTO, String newname) {
        User user = userRepository.findById(userDTO.getId()).orElse(null);
        if(user != null) {
            user.setUsername(newname);
            this.userRepository.save(user);
        }
    }

    public void modifyUserpassword(UserInformationDTO userDTO, String newpassword) {
        User user = userRepository.findById(userDTO.getId()).orElse(null);
        if(user != null) {
            user.setPassword(newpassword);
            this.userRepository.save(user);
        }
    }

    // role 수정은 admin만 가능
    public void modifyUserrole(UserInformationDTO usernow,UserInformationDTO usertarget, String newrole) {
        // user_n = 현재 로그인된 사용자 계정 정보
        User user_n = userRepository.findById(usernow.getId()).orElse(null);
        // user_t = 변경할 사용자의 계정 정보
        User user_t = userRepository.findById(usertarget.getId()).orElse(null);
        if(user_n.getRole().equals("admin")) {
            user_t.setRole(newrole);
            this.userRepository.save(user_t);
        }
        else {
            throw new NoPermission("관리자 권한이 있어야합니다.");
        }
    }

    //모든 유저 받아오기(admin만 가능)
    public List<UserInformationDTO> getAllUser(String id){
        User user_n = userRepository.findById(id).orElse(null);
        if(user_n.getRole().equals("admin")) {
            List<UserInformationDTO> userInformationDTOS = new ArrayList<>();
            List<User> users = userRepository.findAll();
            for(User user: users){
                userInformationDTOS.add(UserInformationDTO.from(user));
            }
            return userInformationDTOS;
        }else{
            throw new NoPermission("관리자 권한이 있어야합니다.");
        }
    }
    //해당 유저와 연관이 있는 프로젝트 아이디들을 반환.
    public List<HashMap<String,String>> mapToUserResponse(UserInformationDTO userInformationDTO) {
        User user = userRepository.findById(userInformationDTO.getId()).orElse(null);
        Set<Project> projectSet = user.getInchargeProjects();

        List<HashMap<String,String>> result = new ArrayList<>();
        if (projectSet.isEmpty()) {
            HashMap<String,String> tmp = new HashMap<String,String>();
            tmp.put("id", user.getId());
            tmp.put("name",user.getUsername());
            tmp.put("role",user.getRole());
            tmp.put("charge","");
            result.add(tmp);
        }else{
            for (Project p : projectSet) {
                HashMap<String,String> tmp = new HashMap<String,String>();
                tmp.put("id", user.getId());
                tmp.put("name",user.getUsername());
                tmp.put("role",user.getRole());
                tmp.put("charge","");
                result.add(tmp);
            }
        }
        return result;
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

    // 코멘트 추가기능
    public void addComment(IssueDTO issueDTO, String comment){

    }
}

///// add comment : 해당 프로젝트에 속한 사용자만 가능