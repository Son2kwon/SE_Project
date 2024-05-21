package swengineering.team7.issuemanagementsystem.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import swengineering.team7.issuemanagementsystem.dto.IssueDTO;
import swengineering.team7.issuemanagementsystem.service.IssueService;

import java.util.List;

@Controller
public class IssueDetailController {
    IssueService issueService;

    @GetMapping("/issue/detail/{id}")
    public String detail(@PathVariable("id") long id, Model model) {
        List<IssueDTO> issue = issueService.findbyIssueID(id);
        model.addAttribute("issue", issue);
        return "Issue_detail";
    }
}
