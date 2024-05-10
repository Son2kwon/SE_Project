package swengineering.team7.issuemanagementsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import swengineering.team7.issuemanagementsystem.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User> {
    //로그인 하기위해 아이디 조회
    Optional<User> findByUsername(String username);
    //사용자 검색
    Page<User> findAll(Specification<User> spec, Pageable pageable);
    // 기본 CRUD 제공
}