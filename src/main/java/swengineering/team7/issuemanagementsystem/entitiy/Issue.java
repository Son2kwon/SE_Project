package swengineering.team7.issuemanagementsystem.entitiy;

import jakarta.persistence.*;
import java.util.Set;

@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String date;
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
    private User user;

    // Issue:Comment 1:다    has many
    @OneToMany(mappedBy = "issue")
    private Set<Comment> comments;

    //Enum으로 Priority 정의
    public enum Priority{
        //LOW, MEDIUM, HIGH, CRITICAL
    }

    //Issue:User 다:다    Assign
    @ManyToMany
    @JoinTable(
            name = "issue_user",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> assignedUsers;


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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
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
}
