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

    @PostMapping("/create/{id}")
    public String createComment(@PathVariable("id") Long id, Model model,
                                @RequestParam(value = "newComment") String newComment,
                                @RequestParam("token") String token) {
        List<IssueDTO> issues = issueService.findbyIssueID(id);
        IssueDTO issue = issues.get(0);
        JwtCertificate jwtCertificate = new JwtCertificate();
        String writer = jwtCertificate.extractId(token);
        User user = userService.SearchSepcificUser(writer);


        CommentDTO commentDTO = commentService.createCommentDTO(null, newComment, writer, LocalDateTime.now(), issue.getId(), user);

        commentService.addComment(commentDTO, issue);

        return String.format("redirect:/issue/detail/%d?token=%s", id,token);
    }

    @ResponseBody
    @PostMapping("/edit/{issueID}/{commentID}")
    public String editComment(@PathVariable("issueID") Long issueID, @PathVariable("commentID") Long commentID, Model model) {
        List<IssueDTO> issues = issueService.findbyIssueID(issueID);
        IssueDTO issue = issues.get(0);

        CommentDTO comment = commentService.getComment(issueID, commentID);
        model.addAttribute("issue", issue);
        model.addAttribute("comment", comment);
        return "EditComment";
    }

    @PostMapping("/edit/complete/{issueID}/{commentID}")
    public String SaveEditedComment(@PathVariable("issueID") Long issueID, @PathVariable("commentID") Long commentID, @RequestParam(value="newContent") String newContent) {
        CommentDTO commentDTO = commentService.getComment(issueID, commentID);
        commentService.modifyComment(commentDTO, newContent, LocalDateTime.now());

        return String.format("redirect:/issue/detail/%d", issueID);
    }
}