package swengineering.team7.issuemanagementsystem.entitiy;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.Date;


@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String body;
    private String writer;
    private LocalDateTime date;

    // Comment:Issue 다:1  has many
    @ManyToOne
    private Issue issue;

    // Comment:User 다:1
    @ManyToOne
    @JoinColumn(name = "user_id") // 외래키 컬럼 이름을 user_id로 설정
    private User user;


    //Getter & Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
