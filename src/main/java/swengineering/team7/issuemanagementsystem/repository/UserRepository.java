package swengineering.team7.issuemanagementsystem.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import swengineering.team7.issuemanagementsystem.entity.User;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, String>, JpaSpecificationExecutor<User> {
    //사용자 검색
    Page<User> findAll(Specification<User> spec, Pageable pageable);
    // 기본 CRUD 제공

    Optional<User> findByUsername(String name);
}
