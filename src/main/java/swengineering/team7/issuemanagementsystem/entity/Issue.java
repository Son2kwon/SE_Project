package swengineering.team7.issuemanagementsystem.entity;

import jakarta.persistence.*;
import swengineering.team7.issuemanagementsystem.util.Priority;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private LocalDateTime date;
    private String state;

    @Lob // 긴 텍스트 지원
    private String issueDescription;

    @Enumerated(EnumType.STRING) //Enum을 문자열로 저장
    private Priority priority;

    // Issue:Project 다:1
    @ManyToOne
    private Project project;

    // Issue:User 다;1   create
    @ManyToOne
    private User reporter;

    //  Issue:User 다;1   최종 Fixer ( 추후 추가 필요)
    //    @ManyToOne
    //    private User fixer;

    // Issue:Comment 1:다    has many
    @OneToMany(mappedBy = "issue")
    private Set<Comment> comments;

    //Enum으로 Priority 정의 (Util 패키지로 리팩토링)
    //    public enum Priority{
    //        //LOW, MEDIUM, HIGH, CRITICAL
    //    }

    //Issue:User 다:다    Assign
    @ManyToMany
    @JoinTable(
            name = "issue_user",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> assignedUsers;

    public Issue(){};


    public static Issue makeIssueOf(String title, String issueDescription,LocalDateTime date, String state){
        Issue issue = new Issue();
        issue.setTitle(title);
        issue.setIssueDescription(issueDescription);
        issue.setDate(date);
        issue.setState(state);
        return issue;
    }


    //Getter & Setter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getReporter() {
        return reporter;
    }

    public void setReporter(User user) {
        this.reporter = user;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public Set<User> getAssignedUsers() {
        return assignedUsers;
    }

    public void setAssignedUsers(Set<User> assignedUsers) {
        this.assignedUsers = assignedUsers;
    }

    public void addCommnet(Comment comment) {
        this.comments.add(comment);
    }
}