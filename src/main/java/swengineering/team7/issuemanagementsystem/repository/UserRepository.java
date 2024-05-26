package swengineering.team7.issuemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swengineering.team7.issuemanagementsystem.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    //사용자 검색
    Page<User> findAll(Specification<User> spec, Pageable pageable);
    // 기본 CRUD 제공
}
