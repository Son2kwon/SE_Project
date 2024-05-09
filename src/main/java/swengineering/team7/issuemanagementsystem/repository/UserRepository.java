package swengineering.team7.issuemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swengineering.team7.issuemanagementsystem.entitiy.User;

public interface UserRepository extends JpaRepository<User, Long> {
    // 기본 CRUD 제공
}
