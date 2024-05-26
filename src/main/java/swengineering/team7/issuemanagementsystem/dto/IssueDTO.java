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
    private String ReporterID;
    private String Reportername;
    private Long projectID;

    public IssueDTO() {
    }

    //Entity -> DTO로 바꿔주는 생성자 메소드
    static public IssueDTO makeDTOFrom(Issue issue){
        return new IssueDTO(issue.getId(), issue.getTitle(), issue.getDate(),
                issue.getState(), issue.getIssueDescription(),issue.getPriority(),
                issue.getReporter().getId(),issue.getReporter().getUsername());
    }

    public IssueDTO(Long id, String title, LocalDateTime date, String state, String issueDescription, Priority priority, String ReporterID, String Reportername) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.state = state;
        this.issueDescription = issueDescription;
        this.priority = priority;
        this.ReporterID = ReporterID;
        this.Reportername = Reportername;
    }

    public IssueDTO(Long id, String state, String issueDescription) {
        this.id = id;
        this.state = state;
        this.issueDescription = issueDescription;
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

    public void setReporterID(String reporterID) {
        this.ReporterID = reporterID;
    }

    public void setReportername(String reportername) {
        this.Reportername = reportername;
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

    public String getReportername() {
        return Reportername;
    }

    public String getReporterID() {
        return ReporterID;
    }
    public Long getProjectID() {
        return projectID;
    }

    public void setProjectID(Long id) {
        this.projectID = id;
    }
}
