package swengineering.team7.issuemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import swengineering.team7.issuemanagementsystem.entity.Project;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    List<Project> findAllByOrderByStartDateDesc();
    // 기본 CRUD 제공
}