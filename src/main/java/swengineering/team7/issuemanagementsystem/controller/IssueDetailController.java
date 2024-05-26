package swengineering.team7.issuemanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import swengineering.team7.issuemanagementsystem.DTOs.IssueDTO;
import swengineering.team7.issuemanagementsystem.service.IssueService;

import java.util.List;

@RequestMapping("/issue")
@Controller
public class IssueDetailController {
    IssueService issueService;

    @ResponseBody
    @PostMapping("/detail/{id}")
    public String detail(@PathVariable("id") Long id, Model model) {
        List<IssueDTO> issue = issueService.findbyIssueID(id);
        model.addAttribute("issue", issue);
        return "IssueDetail";
    }

    @ResponseBody
    @PostMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {
        List<IssueDTO> issue = issueService.findbyIssueID(id);
        model.addAttribute("issue", issue);
        return "EditIssue";
    }

    @PostMapping("/edit/complete/{id}")
    public String SaveEditedIssue(@PathVariable("id") Long id, Model model, @RequestParam(value="newDescription") String newDescription) {
        List<IssueDTO> issues = issueService.findbyIssueID(id);
        IssueDTO issue = issues.get(0);
        issue.setIssueDescription(newDescription);

        issueService.updateDesciprtion(issue);
        issueService.updateState(issue);

        return String.format("redirect:/issue/detail/%d", id);
    }
}