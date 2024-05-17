package swengineering.team7.issuemanagementsystem.dto;

import java.util.List;

public class ProjectAssignedUserDTO {

    private Long projectId;
    private List<Long> assignedUsersID;

    public ProjectAssignedUserDTO(Long projectId, List<Long> assignedUsersID) {
        this.projectId = projectId;
        this.assignedUsersID = assignedUsersID;
    }

    public Long getProjectId() {
        return projectId;
    }

    public List<Long> getAssignedUsersID() {
        return assignedUsersID;
    }
}
