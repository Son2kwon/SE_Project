package swengineering.team7.issuemanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import swengineering.team7.issuemanagementsystem.DTO.IssueDTO;
import swengineering.team7.issuemanagementsystem.DTO.CommentDTO;
import swengineering.team7.issuemanagementsystem.entity.Comment;
import swengineering.team7.issuemanagementsystem.service.CommentService;
import swengineering.team7.issuemanagementsystem.service.IssueService;

import java.time.LocalDateTime;
import java.util.List;


@RequestMapping("/comment")
@Controller
public class CommentController {
    private IssueService issueService;
    private CommentService commentService;

    @PostMapping("/create/{id}")
    public String createComment(@PathVariable("id") Long id, Model model, @RequestParam(value = "newComment") String newComment) {
        List<IssueDTO> issues = issueService.findbyIssueID(id);
        IssueDTO issue = issues.get(0);

        Comment comment = Comment.makeCommentof(newComment, writer, LocalDateTime.now(), issue, user);
        CommentDTO commentDTO = CommentDTO.makeDTOfrom(comment);
        issueService.addComment(commentDTO, issue);

        return String.format("redirect:/issue/detail/%d", id);
    }

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
        issueService.modifyComment(commentDTO, newContent, LocalDateTime.now());

        return String.format("redirect:/issue/detail/%d", issueID);
    }
}