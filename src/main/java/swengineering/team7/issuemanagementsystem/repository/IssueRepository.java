package swengineering.team7.issuemanagementsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import swengineering.team7.issuemanagementsystem.entity.Issue;
import swengineering.team7.issuemanagementsystem.util.Priority;

import java.util.List;
import java.util.Optional;

public interface IssueRepository extends JpaRepository<Issue, Long> {
    // 기본 CRUD 제공
    Optional<Issue> findById(Long id);

    //ID 기준 정렬
    List<Issue> findByTitleContaining(String keyword);
    List<Issue> findByStateContaining(String state);
    List<Issue> findByReporter_usernameContaining(String name);
    List<Issue> findByPriority(Priority priority);

    //최근순 정렬
    List<Issue> findByTitleContainingOrderByDateDesc(String keyword);
    List<Issue> findByStateContainingOrderByDateDesc(String state);
    List<Issue> findByReporter_usernameContainingOrderByDateDesc(String partialUserName);
    List<Issue> findByPriorityOrderByDateDesc(Priority priority);

    //날짜별로 발생한 이슈를 반환하는 메소드
    @Query("SELECT DATE(i.date), COUNT(i) FROM Issue i GROUP BY DATE(i.date)")
    List<Object[]> countIssuesByDate();

}