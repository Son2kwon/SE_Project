package swengineering.team7.issuemanagementsystem.DTO;

import swengineering.team7.issuemanagementsystem.entity.Comment;
import swengineering.team7.issuemanagementsystem.entity.Issue;
import swengineering.team7.issuemanagementsystem.entity.User;

import java.time.LocalDateTime;

public class CommentDTO {
    private Long id;
    private String body;
    private String writer;
    private LocalDateTime date;
    private Issue issue;

    public CommentDTO() {

    }

    public CommentDTO(Long id,String body,String writer,LocalDateTime date,Issue issue) {
        this.id=id;
        this.body=body;
        this.writer=writer;
        this.date=date;
        this.issue=issue;
    }

    public void setId(Long id) {
        this.id=id;
    }

    public void setBody(String body){
        this.body=body;
    }

    public void setWriter(String Writer){
        this.writer=writer;
    }

    public void setDate(LocalDateTime date) {
        this.date=date;
    }

    public void setIssue(Issue issue) {
        this.issue=issue;
    }

    public Long getId() {
        return this.id;
    }

    public String getBody() {
        return this.body;
    }

    public String getWriter() {
        return this.writer;
    }

    public LocalDateTime getDate() {
        return this.date;
    }

    public Issue getIssue() {
        return this.issue;
    }

    public static CommentDTO makeDTOfrom(Comment comment) {
        return new CommentDTO(comment.getId(), comment.getContent(), comment.getWriter(), comment.getDate(),
                comment.getIssue());
    }
}
