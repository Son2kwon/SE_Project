package swengineering.team7.issuemanagementsystem.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import swengineering.team7.issuemanagementsystem.entity.Issue;
import swengineering.team7.issuemanagementsystem.entity.User;
import swengineering.team7.issuemanagementsystem.util.Priority;
import swengineering.team7.issuemanagementsystem.util.State;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface IssueRepository extends JpaRepository<Issue, Long> {
    // 기본 CRUD 제공
    // 기본 CRUD 제공
    Optional<Issue> findById(Long id);
    @EntityGraph(attributePaths = "assignedUsers")
    List<Issue> findAll();
    List<Issue> findByFixerId(String fixerId);
    //ID 기준 정렬
    List<Issue> findByTitleContaining(String keyword);
    List<Issue> findByStateContaining(String state);
    List<Issue> findByReporter_usernameContaining(String name);
    List<Issue> findByPriority(Priority priority);
    //최근순 정렬
    List<Issue> findByTitleContainingOrderByDateDesc(String keyword);
    List<Issue> findByState(State state);
    List<Issue> findByStateOrderByDateDesc(State state);
    List<Issue> findByReporter_usernameContainingOrderByDateDesc(String partialUserName);
    List<Issue> findByPriorityOrderByDateDesc(Priority priority);
    @Query("SELECT i FROM Issue i JOIN i.assignedUsers u WHERE u.id = :userId")
    List<Issue> findByAssignedUserId(String userId);

    //날짜별로 발생한 이슈를 반환하는 메소드
    @Query("SELECT DATE(i.date), COUNT(i) FROM Issue i GROUP BY DATE(i.date)")
    List<Object[]> countIssuesByDate();

    //태그별로 발생한 이슈의 개수를 반환하는 메소드
    @Query("SELECT i.tag, COUNT(i) FROM Issue i GROUP BY i.tag")
    List<Object[]> countIssuesByTag();

    @Query("SELECT i.priority, COUNT(i) FROM Issue i GROUP BY i.priority")
    List<Object[]> countIssuesByPriority();

}