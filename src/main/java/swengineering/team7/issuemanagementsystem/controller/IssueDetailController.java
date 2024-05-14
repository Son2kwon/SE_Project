package swengineering.team7.issuemanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import swengineering.team7.issuemanagementsystem.entitiy.Issue;
import swengineering.team7.issuemanagementsystem.service.IssueService;

@Controller
public class IssueDetailController {
    IssueService issueService;

    @GetMapping("/issue/detail/{id}")
    public String detail(@PathVariable("id") Integer id, Model model) {
        Issue issue = this.issueService.findbyId(id);
        model.addAttribute("issue", issue);
        return "Issue_detail";
    }
}
