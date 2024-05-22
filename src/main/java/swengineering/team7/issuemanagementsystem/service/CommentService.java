package swengineering.team7.issuemanagementsystem.service;

import swengineering.team7.issuemanagementsystem.DTO.CommentDTO;
import swengineering.team7.issuemanagementsystem.entity.Comment;
import swengineering.team7.issuemanagementsystem.entity.Issue;
import swengineering.team7.issuemanagementsystem.repository.CommentRepository;
import swengineering.team7.issuemanagementsystem.repository.IssueRepository;
import swengineering.team7.issuemanagementsystem.repository.ProjectRepository;
import swengineering.team7.issuemanagementsystem.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public class CommentService {

    IssueRepository issueRepository;
    UserRepository userRepository;
    ProjectRepository projectRepository;
    CommentRepository commentRepository;

    public void create(Issue issue, String newComment) {
        Comment comment = new Comment();
        comment.setBody(newComment);
        comment.setDate(LocalDateTime.now());
        comment.setIssue(issue);
        this.commentRepository.save(comment);
    }

    public CommentService(UserRepository userRepository, IssueRepository issueRepository, ProjectRepository projectRepository, CommentRepository commentRepository) {
        this.userRepository = userRepository;
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
        this.commentRepository = commentRepository;
    }

    public CommentDTO getComment(Long IssueID, Long CommentID) {
        Optional<Issue> issue = issueRepository.findById(IssueID);
        Optional<Comment> comment = commentRepository.findById(CommentID);

        // 두 ID로 찾은 Issue와 Comment가 존재하고, Issue에 해당 Comment가 있는경우
        // CommentDTO 생성해서 반환
        if(issue.isPresent()&&comment.isPresent()){
            if(issue.get().getComments().contains(comment.get())){
                return CommentDTO.makeDTOfrom(comment.get());
            }
        }
        return null;
    }
}
