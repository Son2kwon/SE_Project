package swengineering.team7.issuemanagementsystem.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import swengineering.team7.issuemanagementsystem.DTO.UserInformationDTO;
import swengineering.team7.issuemanagementsystem.entity.Admin;
import swengineering.team7.issuemanagementsystem.entity.Project;
import swengineering.team7.issuemanagementsystem.entity.User;
import swengineering.team7.issuemanagementsystem.exception.NoPermission;
import swengineering.team7.issuemanagementsystem.repository.UserRepository;

import java.util.*;


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
       if(userRepository.findById(id).orElse(null)==null) {
           this.userRepository.save(user);
           if(userRepository.count()==1) {
               user.setRole("admin");
               this.userRepository.save(user);
           }
       }
    }
    public boolean login(UserInformationDTO userInformationDTO){
       User user = userRepository.findById(userInformationDTO.getId()).orElse(null);
        return user != null && user.getPassword().equals(userInformationDTO.getPassword());
    }
    public boolean isAdmin(UserInformationDTO userInformationDTO){
       User user = userRepository.findById(userInformationDTO.getId()).orElse(null);
       return user.getRole()!=null&&user.getRole().equals("admin");
    }
    //계정 삭제
    public void deleteUserById(String userId){
       if(userRepository.existsById(userId)) {
           userRepository.deleteById(userId);
       }
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
        if(user_n.getRole()!=null && user_n.getRole().equals("admin")) {
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
    //해당 유저와 연관이 있는 프로젝트 아이디, Role들을 반환.
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
    public List<UserInformationDTO> searchByUsername(String keyword) {
        List<User> users = userRepository.findByUsernameContainingOrderByUsernameAsc(keyword);

        List<UserInformationDTO> userInformationDTOS = new ArrayList<>();
        for (User user : users) {
            userInformationDTOS.add(UserInformationDTO.from(user));
        }

        return userInformationDTOS;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //사용자 권한으로 검색된 리스트 DTO 반환
    public List<UserInformationDTO> searchByUserrole(String role) {
        List<User> users = userRepository.findByRoleOrderByUsernameAsc(role);

        List<UserInformationDTO> userInformationDTOS = new ArrayList<>();
        for (User user : users) {
            userInformationDTOS.add(UserInformationDTO.from(user));
        }

        return userInformationDTOS;
    }

    public User SearchSepcificUser (String id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(null);
    }
}
