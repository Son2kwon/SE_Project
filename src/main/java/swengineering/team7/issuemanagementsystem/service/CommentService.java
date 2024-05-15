package swengineering.team7.issuemanagementsystem.service;

import org.springframework.stereotype.Service;
import swengineering.team7.issuemanagementsystem.entitiy.Comment;
import swengineering.team7.issuemanagementsystem.entitiy.Issue;
import swengineering.team7.issuemanagementsystem.repository.CommentRepository;

import java.time.LocalDateTime;

@Service
public class CommentService {
    private CommentRepository commentRepository;

    public void create(Issue issue, String newComment) {
        Comment comment = new Comment();
        comment.setBody(newComment);
        comment.setDate(LocalDateTime.now());
        comment.setIssue(issue);
        this.commentRepository.save(comment);
    }
}
