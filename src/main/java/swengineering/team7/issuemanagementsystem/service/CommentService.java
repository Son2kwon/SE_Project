package swengineering.team7.issuemanagementsystem.service;

import swengineering.team7.issuemanagementsystem.DTO.IssueDTO;
import swengineering.team7.issuemanagementsystem.DTO.ProjectDTO;
import swengineering.team7.issuemanagementsystem.DTO.UserInformationDTO;
import swengineering.team7.issuemanagementsystem.entity.Comment;
import swengineering.team7.issuemanagementsystem.entity.Issue;
import swengineering.team7.issuemanagementsystem.entity.Project;
import swengineering.team7.issuemanagementsystem.entity.User;
import swengineering.team7.issuemanagementsystem.exception.NoBelong;
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

    public Boolean addComment(ProjectDTO p, IssueDTO i, UserInformationDTO u,
                              String content, String Writer, LocalDateTime Time) {
        Optional<Issue> I=issueRepository.findById(i.getId());
        Optional<Project> P=projectRepository.findById(p.getId());
        Optional<User> U=userRepository.findById(u.getId());
        if(I.isPresent()&&P.isPresent()&&U.isPresent()) {
            Issue issue=I.get();
            Project project=P.get();
            User user=U.get();
            //User가 해당 프로젝트에 소속되어있는 경우
            if(project.getAssignedUsers().contains(user)) {
                Comment newcomment=Comment.makeCommentof(content,Writer,Time,issue,user);
                issue.addCommnet(newcomment);
                user.addComment(newcomment);
                this.issueRepository.save(issue);
                this.commentRepository.save(newcomment);
                this.userRepository.save(user);
            }
            else {
                throw new NoBelong("해당 프로젝트에 소속되어있지 않은 사용자입니다.");
            }
        }
        return false;
    }
}