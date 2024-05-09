package swengineering.team7.issuemanagementsystem.entity;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "User")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String password;
    private String role;

    // User:Project 다:다     두 엔터티 연결하는 중간 테이블 생성
    @ManyToMany
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> inchargeProjects;

    // User:Issue 1:다   create
    @OneToMany(mappedBy = "user")
    private Set<Issue> issues;

    // User:Issue 1:다   Assign
    @ManyToMany(mappedBy = "assignedUsers")
    private Set<Issue> assignedIssues;

    // User:Comment 1:다
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;



    //Getter & Setter
    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }

    public Set<Issue> getIssues() {
        return issues;
    }

    public String getPassword() {
        return password;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String name) {
        this.username = name;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
    }

    public Set<Issue> getAssignedIssues() {
        return assignedIssues;
    }

    public void setAssignedIssues(Set<Issue> assignedIssues) {
        this.assignedIssues = assignedIssues;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Project> getInchargeProjects() {
        return inchargeProjects;
    }

    public void setInchargeProjects(Set<Project> inchargeProjects) {
        this.inchargeProjects = inchargeProjects;
    }

    public void setPassword(String passowrd)
    {
        this.password = passowrd;
    }
}