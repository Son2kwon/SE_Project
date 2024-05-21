package swengineering.team7.issuemanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swengineering.team7.issuemanagementsystem.dto.IssueDTO;
import swengineering.team7.issuemanagementsystem.service.CommentService;
import swengineering.team7.issuemanagementsystem.service.IssueService;

import java.util.List;

@RequestMapping("/comments")
@Controller
public class CommentController {
    private IssueService issueService;
    private CommentService commentService;
    @PostMapping("/create/{id}")
    public String createComment(@PathVariable("id") long id, Model model, @RequestParam(value = "newComment") String newComment) {
        List<IssueDTO> issues = this.issueService.findbyIssueID(id);
        IssueDTO issue = issues.get(0);
        return String.format("redirect:/issue/detail/%d", id);
    }
}
