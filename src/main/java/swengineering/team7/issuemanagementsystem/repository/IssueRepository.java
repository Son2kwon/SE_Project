package swengineering.team7.issuemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import swengineering.team7.issuemanagementsystem.entitiy.Issue;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    // 기본 CRUD 제공

    //ID 기준 정렬
    List<Issue> findByTitleContaining(String keyword);
    List<Issue> findByStateContaining(String state);
    List<Issue> findByReporter_NameContaining(String name);

    //최근순 정렬
    List<Issue> findByTitleContainingOrderByDateDesc(String keyword);
    List<Issue> findByStateContainingOrderByDateDesc(String state);
    List<Issue> findByReporter_NameContainingOrderByDateDesc(String partialUserName);

}

