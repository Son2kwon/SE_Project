package swengineering.team7.issuemanagementsystem.entity;


import jakarta.persistence.*;
import org.springframework.cglib.core.Local;
import swengineering.team7.issuemanagementsystem.entity.User;

import java.time.LocalDateTime;
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
    private Set<User> assignedUsers;

    // Project:Issue   1:다      has many
    @OneToMany(mappedBy = "project")
    private Set<Issue> issues;

    // 생성자 메소드
    public static Project makeProjectOf(String name, LocalDateTime startDate, LocalDateTime dueDate ){
        Project project = new Project();
        project.setName(name);
        project.setStartDate(startDate);
        project.setDueDate(dueDate);
        return project;
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
        assignedUsers.add(user);
    }

    public void addIssue(Issue issue){
        issues.add(issue);
    }

    public void removeIssue(Issue issue){
        issues.remove(issue);
    }

}