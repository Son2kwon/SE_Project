package swengineering.team7.issuemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import swengineering.team7.issuemanagementsystem.DTO.CommentDTO;
import swengineering.team7.issuemanagementsystem.DTO.IssueDTO;
import swengineering.team7.issuemanagementsystem.service.CommentService;
import swengineering.team7.issuemanagementsystem.service.IssueService;
import swengineering.team7.issuemanagementsystem.service.ProjectAssignmentService;
import swengineering.team7.issuemanagementsystem.util.JwtCertificate;
import swengineering.team7.issuemanagementsystem.util.Role;
import swengineering.team7.issuemanagementsystem.util.State;

import java.util.List;
import java.util.Set;

@RequestMapping("/issue")
@Controller
public class IssueDetailController {
    IssueService issueService;
    CommentService commentService;
    ProjectAssignmentService projectAssignmentService;
    @Autowired
    public IssueDetailController(IssueService issueService, CommentService commentService, ProjectAssignmentService projectAssignmentService) {
        this.issueService=issueService;
        this.commentService=commentService;
        this.projectAssignmentService=projectAssignmentService;
    }
    @GetMapping("/detail/{projectId}/{id}")
    public String detail(
            @PathVariable("projectId") Long projectId,
            @PathVariable("id") Long id,
            @RequestParam("token") String token,
            Model model) {
        JwtCertificate jwtCertificate = new JwtCertificate();
        String userID = jwtCertificate.extractId(token);

        Role role = projectAssignmentService.getRoleByProjectIdAndUserId(projectId,userID);
        List<IssueDTO> issues = issueService.findbyIssueID(id);
        IssueDTO issue = issues.get(0);
        List<CommentDTO> commentDTOs = commentService.sortCommentsByDate(commentService.getAllCommentsByIssueID(issue.getId()));

        issue.setComments(commentDTOs);

        model.addAttribute("issue", issue);
        model.addAttribute("state", issue.getState().toString());
        model.addAttribute("token", token);
        model.addAttribute("projectId", projectId);
        model.addAttribute("userID", userID);
        model.addAttribute("role",role.toString());
        return "IssueDetailAndEdit";
    }

    @PostMapping("/edit/complete/{projectId}/{id}")
    public String SaveEditedIssue(@PathVariable("projectId") Long projectId,
                                  @PathVariable("id") Long id, Model model,
                                  @RequestParam("token") String token,
                                  @RequestParam("selectState") String newState,
                                  @RequestParam(value="newDescription") String newDescription
                                  ) {
        JwtCertificate jwtCertificate = new JwtCertificate();
        String updaterID = jwtCertificate.extractId(token);
        IssueDTO issue = issueService.findbyIssueID(id).get(0);
        issue.setState(State.valueOf(newState));
        issue.setIssueDescription(newDescription);
        issueService.updateDesciprtion(issue);
        issueService.updateState(issue,updaterID);
        return String.format("redirect:/issue/detail/%d/%d?token=%s", projectId,id,token);
    }
}