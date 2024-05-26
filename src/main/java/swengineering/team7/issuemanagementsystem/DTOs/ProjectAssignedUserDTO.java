package swengineering.team7.issuemanagementsystem.DTOs;

import java.util.List;

public class ProjectAssignedUserDTO {

    private Long projectId;
    private List<String> assignedUsersID;

    public ProjectAssignedUserDTO(Long projectId, List<String> assignedUsersID) {
        this.projectId = projectId;
        this.assignedUsersID = assignedUsersID;
    }

    public Long getProjectId() {
        return projectId;
    }

    public List<String> getAssignedUsersID() {
        return assignedUsersID;
    }

}
