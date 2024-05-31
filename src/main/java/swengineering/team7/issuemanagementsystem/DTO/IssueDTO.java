package swengineering.team7.issuemanagementsystem.DTO;

import swengineering.team7.issuemanagementsystem.entity.User;
import swengineering.team7.issuemanagementsystem.util.Priority;
import swengineering.team7.issuemanagementsystem.util.State;
import swengineering.team7.issuemanagementsystem.DTO.CommentDTO;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class IssueDTO {

    private Long id;
    private String title;
    private LocalDateTime date;
    private State state;
    private String issueDescription;
    private Priority priority;
    private String ReporterID;
    private String Reportername;
    private Long projectID;
    private String tag;
    private String fixer;
    private String fixerName;
    private Set<String> assignees;

    private List<CommentDTO> comments;


    public IssueDTO() {
    }

    //Entity -> DTO로 바꿔주는 생성자 메소드
    static public IssueDTO makeDTOFrom(swengineering.team7.issuemanagementsystem.entity.Issue issue){
        if(issue.getFixer()!=null){
        return new IssueDTO(issue.getId(),issue.getProject().getId(), issue.getTitle(), issue.getDate(),
                issue.getState(), issue.getIssueDescription(),issue.getPriority(),
                issue.getReporter().getId(),issue.getReporter().getUsername(),issue.getTag(),
                issue.getFixer().getId(), issue.getFixer().getUsername(),
                issue.getAssignedUsers().stream()
                    .map(User::getId)
                    .collect(Collectors.toSet())
                );}

        else return new IssueDTO(issue.getId(), issue.getProject().getId(), issue.getTitle(), issue.getDate(),
                issue.getState(), issue.getIssueDescription(),issue.getPriority(),
                issue.getReporter().getId(),issue.getReporter().getUsername(),issue.getTag(),
                "", "",
                issue.getAssignedUsers().stream()
                      .map(User::getId)
                      .collect(Collectors.toSet())
                 );
    }

    public IssueDTO(Long id, Long projectID, String title, LocalDateTime date, State state, String issueDescription, Priority priority, String ReporterID, String Reportername,
                    String tag) {
        this.id = id;
        this.projectID = projectID;
        this.title = title;
        this.date = date;
        this.state = state;
        this.issueDescription = issueDescription;
        this.priority = priority;
        this.ReporterID = ReporterID;
        this.Reportername = Reportername;
        this.tag = tag;
    }

    public IssueDTO(Long id, Long projectID, String title, LocalDateTime date, State state, String issueDescription, Priority priority, String ReporterID, String Reportername,
                    String tag, String fixerId, String fixerName, Set<String> assignees) {
        this.id = id;
        this.projectID = projectID;
        this.title = title;
        this.date = date;
        this.state = state;
        this.issueDescription = issueDescription;
        this.priority = priority;
        this.ReporterID = ReporterID;
        this.Reportername = Reportername;
        this.tag = tag;
        this.fixer = fixerId;
        this.fixerName = fixerName;
        this.assignees = assignees;
    }

    public IssueDTO(Long id, State state, String issueDescription) {
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

    public void setState(State state) {
        this.state = state;
    }

    public void setTag(String tag) { this.tag = tag; }

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

    public State getState() {
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
    public String getFixer(){return fixer;}
    public String getFixerName(){return fixerName;}

    public Long getProjectID() {
        return projectID;
    }

    public void setProjectID(Long id) {
        this.projectID = id;
    }

    public String getTag() { return tag; }
    public void setAssignees(Set<String> assignees){this.assignees=assignees;}
    public Set<String> getAssignees(){return this.assignees;}

    public List<CommentDTO> getComments(){return this.comments;}
    public void setComments(List<CommentDTO> comments){this.comments=comments;}
}