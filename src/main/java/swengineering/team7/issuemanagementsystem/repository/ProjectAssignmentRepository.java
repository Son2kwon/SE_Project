package swengineering.team7.issuemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swengineering.team7.issuemanagementsystem.entity.ProjectAssignment;
import swengineering.team7.issuemanagementsystem.entity.ProjectAssignmentKey;
import swengineering.team7.issuemanagementsystem.util.Role;

import java.util.List;

@Repository
public interface ProjectAssignmentRepository extends JpaRepository<ProjectAssignment, ProjectAssignmentKey> {
    // Add custom query methods if needed
    List<ProjectAssignment> findByIdUserId(String userId);
    List<ProjectAssignment> findByProjectIdAndRole(Long projectId, Role role);
}
