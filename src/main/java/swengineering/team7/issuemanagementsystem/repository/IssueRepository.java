package swengineering.team7.issuemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swengineering.team7.issuemanagementsystem.entitiy.Issue;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    // 기본 CRUD 제공
}
