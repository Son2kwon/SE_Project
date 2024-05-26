package swengineering.team7.issuemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swengineering.team7.issuemanagementsystem.entity.Comment;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    // 기본 CRUD 제공
}
