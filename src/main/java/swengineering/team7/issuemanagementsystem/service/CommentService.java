package swengineering.team7.issuemanagementsystem.service;

import swengineering.team7.issuemanagementsystem.DTO.IssueDTO;
import swengineering.team7.issuemanagementsystem.dto.ProjectDTO;
import swengineering.team7.issuemanagementsystem.entity.Comment;
import swengineering.team7.issuemanagementsystem.entity.Issue;
import swengineering.team7.issuemanagementsystem.entity.User;
import swengineering.team7.issuemanagementsystem.DTO.CommentDTO;

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

    //Comment Create 할때 issue랑 user에도 업데이트해야하나요?
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
    
    public Boolean addComment(CommentDTO commentDTO, IssueDTO issueDTO) {
        Comment comment = Comment.makeCommentof(commentDTO.getBody(),commentDTO.getWriter(),commentDTO.getDate(),commentDTO.getIssue(),commentDTO.getUser());
        //올바른 comment 객체가 입력된경우
        if(commentDTO.getIssue() != null) {
            Issue issue = issueRepository.findById(issueDTO.getId()).orElse(null);
            //Comment 객체가 올바른  Issue에 추가되어야함
            if(issue != null && issue == comment.getIssue()) {
                issue.addComment(comment);
                issueRepository.save(issue);
                commentRepository.save(comment);
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public Boolean modifyComment(CommentDTO dto, String content, LocalDateTime time) {
        Optional<Comment> C=commentRepository.findById(dto.getId());
        if(C.isPresent()) {
            Comment comment=C.get();
            comment.setBody(content);
            comment.setDate(time);
            this.commentRepository.save(comment);
            return true;
        }
        return false;
    }

    public Boolean deleteComment(CommentDTO dto) {
        Optional<Comment> C=commentRepository.findById(dto.getId());
        if(C.isPresent()) {
            Comment comment=C.get();
            Issue issue=comment.getIssue();
            User user=comment.getUser();
            issue.getComments().remove(comment);
            user.getComments().remove(comment);
            this.issueRepository.save(issue);
            this.commentRepository.delete(comment);
            this.userRepository.save(user);
            return true;
        }
        return false;
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
