package swengineering.team7.issuemanagementsystem.entity;

import jakarta.persistence.*;

import swengineering.team7.issuemanagementsystem.util.Priority;
import swengineering.team7.issuemanagementsystem.util.State;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Set;

@Entity
public class Issue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private LocalDateTime date;
    @Enumerated(EnumType.STRING)
    private State state;

    private String tag;

    @Lob // 긴 텍스트 지원
    private String issueDescription;

    @Enumerated(EnumType.STRING) //Enum을 문자열로 저장
    private Priority priority;

    // Issue:Project 다:1
    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    // Issue:User 다;1   create
    @ManyToOne(fetch = FetchType.LAZY)
    private User reporter;

    // Issue:User 다;1   최종 Fixer
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fixer_id")
    private User fixer;

    // Issue:Comment 1:다    has many
    @OneToMany(mappedBy = "issue")
    private Set<Comment> comments;

    //Issue:User 다:다    Assign
    @ManyToMany
    @JoinTable(
            name = "issue_user",
            joinColumns = @JoinColumn(name = "issue_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private Set<User> assignedUsers;

    public Issue(){};


    public static Issue makeIssueOf(String title, String issueDescription,LocalDateTime date, State state, Priority priority){
        Issue issue = new Issue();
        issue.setTitle(title);
        issue.setIssueDescription(issueDescription);
        issue.setDate(date);
        issue.setState(state);
        issue.setPriority(priority);
        return issue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return Objects.equals(id, issue.id) && Objects.equals(title, issue.title) && Objects.equals(date, issue.date) && Objects.equals(state, issue.state) && Objects.equals(issueDescription, issue.issueDescription) && priority == issue.priority && Objects.equals(project, issue.project) && Objects.equals(reporter, issue.reporter) && Objects.equals(comments, issue.comments) && Objects.equals(assignedUsers, issue.assignedUsers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, date, state, issueDescription, priority, project, reporter, comments, assignedUsers);
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

    public State getState() {
        return state;
    }

    public void setState(State state) {
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

    public User getFixer() { return fixer; }

    public void setFixer(User fixer) { this.fixer = fixer;}

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

    public void addComment(Comment comment) {
        this.comments.add(comment);
    }

    public void setTag(String tag) { this.tag = tag; }

    public String getTag() { return tag; }

}
