package swengineering.team7.issuemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swengineering.team7.issuemanagementsystem.entity.Comment;

public interface ProjectRepository extends JpaRepository<Comment, Long> {
    // 기본 CRUD 제공
}