package swengineering.team7.issuemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.UserDetailsServiceAutoConfiguration;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import swengineering.team7.issuemanagementsystem.DTO.IssueDTO;
import swengineering.team7.issuemanagementsystem.DTO.CommentDTO;
import swengineering.team7.issuemanagementsystem.entity.Comment;
import swengineering.team7.issuemanagementsystem.entity.User;
import swengineering.team7.issuemanagementsystem.repository.UserRepository;
import swengineering.team7.issuemanagementsystem.service.CommentService;
import swengineering.team7.issuemanagementsystem.service.IssueService;
import swengineering.team7.issuemanagementsystem.service.UserService;
import swengineering.team7.issuemanagementsystem.util.JwtCertificate;

import java.time.LocalDateTime;
import java.util.List;


@RequestMapping("/comment")
@Controller
public class CommentController {

    private IssueService issueService;
    private CommentService commentService;
    private UserService userService;

    @Autowired
    public CommentController(IssueService issueService,CommentService commentService,UserService userService ) {
        this.issueService=issueService;
        this.userService=userService;
        this.commentService=commentService;
    }

    @PostMapping("/create/{issueId}")
    public String createComment(@PathVariable("issueId") Long issueId, Model model,
                                @RequestParam(value = "newComment") String newComment,
                                @RequestParam("token") String token,
                                @RequestParam("projectId")Long projectId) {
        List<IssueDTO> issues = issueService.findbyIssueID(issueId);
        IssueDTO issue = issues.get(0);
        JwtCertificate jwtCertificate = new JwtCertificate();
        String writer = jwtCertificate.extractId(token);
        User user = userService.SearchSepcificUser(writer);


        CommentDTO commentDTO = commentService.createCommentDTO(null, newComment, writer, LocalDateTime.now(), issue.getId(), user);
        if(!newComment.equals(""))
            commentService.addComment(commentDTO, issue);

        return String.format("redirect:/issue/detail/%d/%d?token=%s", projectId,issueId,token);
    }
    @PostMapping("/delete/{commentID}")
    public String deleteComment(@PathVariable("commentID")Long commentID,
                                @RequestParam("projectId")Long projectId,
                                @RequestParam("issueID")Long issueID,
                                @RequestParam("token")String token){
        commentService.deleteComment(commentService.getComment(commentID));
        return String.format("redirect:/issue/detail/%d/%d?token=%s", projectId,issueID,token);
    }

    @GetMapping("/edit/{commentID}")
    public String editComment(@PathVariable("commentID") Long commentID, Model model) {
        CommentDTO comment = commentService.getComment(commentID);
        model.addAttribute("comment", comment);
        return "EditComment";
    }

    @PostMapping("/edit/complete/{commentID}")
    public String SaveEditedComment( @PathVariable("commentID") Long commentID,
                                     @RequestParam("issueID") Long issueID,
                                     @RequestParam("commentContent") String newContent,
                                     @RequestParam("token") String token,
                                     @RequestParam("projectId")Long projectId) {

        CommentDTO commentDTO = commentService.getComment(commentID);
        commentService.modifyComment(commentDTO, newContent, LocalDateTime.now());

        return String.format("redirect:/issue/detail/%d/%d?token=%s",projectId,issueID,token);
    }
}