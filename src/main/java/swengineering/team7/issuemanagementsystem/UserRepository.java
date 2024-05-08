package swengineering.team7.issuemanagementsystem;

import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.domain.Specification;

public interface UserRepository extends JpaRepository<User, String> {
    Page<User> findAll(Specification<User> spec, Pageable pageable);
}
