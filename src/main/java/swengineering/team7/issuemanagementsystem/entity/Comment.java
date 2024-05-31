package swengineering.team7.issuemanagementsystem.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;


@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String content;
    private String writer;
    private LocalDateTime date;

    // Comment:Issue 다:1  has many
    @ManyToOne
    private Issue issue;

    // Comment:User 다:1
    @ManyToOne
    @JoinColumn(name = "user_id") // 외래키 컬럼 이름을 user_id로 설정
    private User user;

    public static Comment makeCommentof(String body,String writer,LocalDateTime date,
                                        Issue issue, User user) {
        Comment comment=new Comment();
        comment.setContent(body);
        comment.setWriter(writer);
        comment.setDate(date);
        comment.setIssue(issue);
        comment.setUser(user);
        return comment;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Comment comment = (Comment) o;
        return Objects.equals(id, comment.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    //Getter & Setter

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public void setBody(String newComment) {
        this.content=newComment;
    }
}