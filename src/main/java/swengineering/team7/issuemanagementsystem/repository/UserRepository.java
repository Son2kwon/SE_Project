package swengineering.team7.issuemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swengineering.team7.issuemanagementsystem.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    //로그인 하기위해 아이디 조회
    Optional<User> findByusername(String username);
    // 기본 CRUD 제공
}