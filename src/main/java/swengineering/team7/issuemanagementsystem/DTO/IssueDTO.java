package swengineering.team7.issuemanagementsystem.DTO;

import swengineering.team7.issuemanagementsystem.util.Priority;

import java.time.LocalDateTime;

public class IssueDTO {

    private Long id;
    private String title;
    private LocalDateTime date;
    private String state;
    private String issueDescription;
    private Priority priority;
    private String UserID;
    private String Username;
    private Long projectID;

    private String fixer;
    private String fixerName;


    public IssueDTO() {
    }

    //Entity -> DTO로 바꿔주는 생성자 메소드
    static public IssueDTO makeDTOFrom(swengineering.team7.issuemanagementsystem.entity.Issue issue){
        if(issue.getFixer()!=null){
        return new IssueDTO(issue.getId(), issue.getTitle(), issue.getDate(),
                issue.getState(), issue.getIssueDescription(),issue.getPriority(),
                issue.getReporter().getId(),issue.getReporter().getUsername(),
                issue.getFixer().getId(), issue.getFixer().getUsername());}
        else return new IssueDTO(issue.getId(), issue.getTitle(), issue.getDate(),
                issue.getState(), issue.getIssueDescription(),issue.getPriority(),
                issue.getReporter().getId(),issue.getReporter().getUsername(),
                "", "");
    }

    public IssueDTO(Long id, String title, LocalDateTime date, String state, String issueDescription, Priority priority, String UserID, String Username,
                    String fixerId, String fixerName) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.state = state;
        this.issueDescription = issueDescription;
        this.priority = priority;
        this.UserID = UserID;
        this.Username = Username;
        this.fixer = fixerId;
        this.fixerName = fixerName;
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

    public void setUserID(String userID) {
        this.UserID = userID;
    }

    public void setUsername(String username) {
        this.Username = username;
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

    public String getUsername() {
        return Username;
    }

    public String getUserID() {
        return UserID;
    }
    public String getFixer(){return fixer;}
    public String getFixerName(){return fixerName;}

    public Long getProjectID() {
        return projectID;
    }

    public void setProjectID(Long id) {
        this.projectID = id;
    }
}
