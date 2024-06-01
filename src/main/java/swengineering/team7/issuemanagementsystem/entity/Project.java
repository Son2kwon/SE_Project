package swengineering.team7.issuemanagementsystem.entity;


import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;

    //Project:User 다:다      inchargeProjects 테이블 사용
    @ManyToMany (mappedBy = "inchargeProjects")
    private Set<User> assignedUsers = new HashSet<>();

    // Project:Issue   1:다      has many
    @OneToMany(mappedBy = "project")
    private Set<Issue> issues = new HashSet<>();

    // 트렌드 필드 추가
    private String trend;

    // 생성자 메소드
    public static Project makeProjectOf(String name, LocalDateTime startDate, LocalDateTime dueDate ){
        Project project = new Project();
        project.setName(name);
        project.setStartDate(startDate);
        project.setDueDate(dueDate);
        return project;
    }

   @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
    
    //Getter & Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }

    public Set<Issue> getIssues() {
        return issues;
    }

    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
    }

    public Set<User> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(Set<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public void addAssignedUser(User user){
        this.assignedUsers.add(user);
    }

    public void addIssue(Issue issue){
        issues.add(issue);
    }

    public void removeIssue(Issue issue){
        issues.remove(issue);
    }

    public String getTrend() {
        return trend;
    }

    public void setTrend(String trend) {
        this.trend = trend;
    }

}
