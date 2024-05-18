package swengineering.team7.issuemanagementsystem.DTO;

import swengineering.team7.issuemanagementsystem.entity.Issue;
import swengineering.team7.issuemanagementsystem.util.Priority;

import java.time.LocalDateTime;

public class IssueDTO {

    private Long id;
    private String title;
    private LocalDateTime date;
    private String state;
    private String issueDescription;
    private Priority priority;
    private Long createUserID;
    private String createUsername;
    private Long ProjectID;

    public IssueDTO() {
    }

    //Entity -> DTO로 바꿔주는 생성자 메소드
    static public IssueDTO makeDTOFrom(Issue issue){
        return new IssueDTO(issue.getId(), issue.getTitle(), issue.getDate(),
                issue.getState(), issue.getIssueDescription(),issue.getPriority(),
                issue.getReporter().getId(),issue.getReporter().getUsername());
    }

    public IssueDTO(Long id, String title, LocalDateTime date, String state, String issueDescription, Priority priority, Long createUserID, String createUsername) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.state = state;
        this.issueDescription = issueDescription;
        this.priority = priority;
        this.createUserID = createUserID;
        this.createUsername = createUsername;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setIssueDescription(String issueDescription) {
        this.issueDescription = issueDescription;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public void setCreateUserID(Long createUserID) {
        this.createUserID = createUserID;
    }

    public void setCreateUsername(String createUsername) {
        this.createUsername = createUsername;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public String getState() {
        return state;
    }

    public String getIssueDescription() {
        return issueDescription;
    }

    public Priority getPriority() {
        return priority;
    }

    public String getCreateUsername() {
        return createUsername;
    }

    public Long getCreateUserID() {
        return createUserID;
    }

    public Long getProjectID() {
        return ProjectID;
    }

    public void setProjectID(Long id) {

    }
}