package swengineering.team7.issuemanagementsystem.DTO;

import swengineering.team7.issuemanagementsystem.entity.Comment;
import swengineering.team7.issuemanagementsystem.entity.Issue;
import swengineering.team7.issuemanagementsystem.entity.User;

import java.util.Set;

public class UserOtherDTO {
    private Set<Issue> issues;
    private Set<Issue> assignedIssues;
    private Set<Comment> comments;

    public UserOtherDTO (Set<Issue> issues, Set<Issue> assignedIssues, Set<Comment> comments) {
        this.issues=issues;
        this.assignedIssues=assignedIssues;
        this.comments=comments;
    }

    public UserOtherDTO () {

    }

    public void setIssues (Set<Issue> issues) {
        this.issues=issues;
    }

    public Set<Issue> getIssues() {
        return this.issues;
    }

    public void setAssignedIssues(Set<Issue> assignedIssues) {
        this.assignedIssues=assignedIssues;
    }

    public Set<Issue> getAssignedIssues() {
        return this.assignedIssues;
    }

    public void setComments(Set<Comment> comments) {
        this.comments = comments;
    }

    public Set<Comment> getComments() {
        return this.comments;
    }

    public static UserOtherDTO makeDTOfrom(User user) {
        UserOtherDTO userOtherDTO = new UserOtherDTO();
        userOtherDTO.setIssues(user.getIssues());
        userOtherDTO.setAssignedIssues(user.getAssignedIssues());
        userOtherDTO.setComments(user.getComments());
        return userOtherDTO;
    }
}
