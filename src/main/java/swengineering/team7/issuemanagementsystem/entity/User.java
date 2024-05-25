package swengineering.team7.issuemanagementsystem.entity;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name = "User")
public class User {
    @Id
    private String id;

    @Column(unique = true)
    private String username;
    private String password;
    private String role;
    private String Contract;
    public User(){}
    public User(String id, String username, String password, String contract){
        this.id = id;
        this.username=username;
        this.password=password;
        this.Contract=contract;
        //test: 일단은 admin
        this.role = "admin";
    }
    // User:Project 다:다     두 엔터티 연결하는 중간 테이블 생성
    @ManyToMany
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> inchargeProjects;

    // User:Issue 1:다   create
    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL)
    private Set<Issue> issues;

    // User:Issue 1:다   Assign
    @ManyToMany(mappedBy = "assignedUsers")
    private Set<Issue> assignedIssues;

    // User:Comment 1:다
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Comment> comments;



    //Getter & Setter
    public String getId() {
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

    public String getContract() {
        return Contract;
    }

    public void setId(String id) {
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

    public void setPassword(String passowrd) {
        this.password = passowrd;
    }

    public void setContract(String contract) {
        this.Contract=contract;
    }

    public void addIssue(Issue newissue) {
        issues.add(newissue);
    }

    public void removeIssue(Issue target) {
        issues.remove(target);
    }

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }


    public void addinchargeProject(Project project){
        inchargeProjects.add(project);
    }

    public void removeinchargeProjects(Project project){
        inchargeProjects.remove(project);
    }


}