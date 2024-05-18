package swengineering.team7.issuemanagementsystem.service;

import org.springframework.stereotype.Service;
import swengineering.team7.issuemanagementsystem.dto.IssueDTO;
import swengineering.team7.issuemanagementsystem.entitiy.Comment;
import swengineering.team7.issuemanagementsystem.entitiy.Issue;
import swengineering.team7.issuemanagementsystem.repository.CommentRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CommentService {
    private CommentRepository commentRepository;

    public void create(List<IssueDTO> issue, String newComment) {
        Comment comment = new Comment();
        comment.setBody(newComment);
        comment.setDate(LocalDateTime.now());
        comment.setIssue((Issue) issue);
        this.commentRepository.save(comment);
    }
}
