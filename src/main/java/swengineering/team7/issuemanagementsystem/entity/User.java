package swengineering.team7.issuemanagementsystem.entity;

import jakarta.persistence.*;

import java.util.Set;


@Entity
@Table(name="member")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String name;
    private String contract;

    // User:Project 다:다     두 엔터티 연결하는 중간 테이블 생성
    @ManyToMany
    @JoinTable(
            name = "user_project",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "project_id")
    )
    private Set<Project> inchargeProjects;

    // User:Issue 1:다   create
    @OneToMany(mappedBy = "reporter")
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

    public String getName() {
        return name;
    }

    public String getContract() {
        return contract;
    }

    public Set<Issue> getIssues() {
        return issues;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setContract(String contract) {
        this.contract = contract;
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

    public void addIssue(Issue issue){
        issues.add(issue);
    }

    public void removeIssue(Issue issue) {
        issues.remove(issue);
    }

    public void addinchargeProject(Project project){
        inchargeProjects.add(project);
    }

    public void removeinchargeProjects(Project project){
        inchargeProjects.remove(project);
    }

}
