package swengineering.team7.issuemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import swengineering.team7.issuemanagementsystem.DTO.CommentDTO;
import swengineering.team7.issuemanagementsystem.DTO.IssueDTO;
import swengineering.team7.issuemanagementsystem.service.CommentService;
import swengineering.team7.issuemanagementsystem.service.IssueService;
import swengineering.team7.issuemanagementsystem.util.State;

import java.util.List;
import java.util.Set;

@RequestMapping("/issue")
@Controller
public class IssueDetailController {
    IssueService issueService;
    CommentService commentService;
    @Autowired
    public IssueDetailController(IssueService issueService, CommentService commentService) {
        this.issueService=issueService;
        this.commentService=commentService;
    }
    @GetMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model, @RequestParam("token") String token) {
        List<IssueDTO> issues = issueService.findbyIssueID(id);
        IssueDTO issue = issues.get(0);
        Set<CommentDTO> commentDTOSet = commentService.getAllCommentsByIssueID(issue.getId());
        issue.setComments(commentDTOSet);

        model.addAttribute("issue", issue);
        model.addAttribute("token", token);
        return "IssueDetail";
    }
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        IssueDTO issue = issueService.findbyIssueID(id).get(0);
        model.addAttribute("issue", issue);
        return "EditIssue";
    }

    @PostMapping("/edit/complete/{id}")
    public String SaveEditedIssue(@PathVariable("id") Long id, Model model,
                                  @RequestParam(value="newDescription") String newDescription,
                                  @RequestParam("state") String newState) {
        IssueDTO issue = issueService.findbyIssueID(id).get(0);
        issue.setState(State.valueOf(newState));
        issue.setIssueDescription(newDescription);
        issueService.updateDesciprtion(issue);
        issueService.updateState(issue);

        return String.format("redirect:/issue/detail/%d", id);
    }
}