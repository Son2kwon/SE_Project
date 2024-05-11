package swengineering.team7.issuemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swengineering.team7.issuemanagementsystem.entitiy.Comment;
import swengineering.team7.issuemanagementsystem.entitiy.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    // 기본 CRUD 제공
}
