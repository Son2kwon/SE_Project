package swengineering.team7.issuemanagementsystem.DTO;

import swengineering.team7.issuemanagementsystem.util.Role;

public class ProjectAssignmentDTO {
    private ProjectDTO projectDTO;
    private String userId;
    private Role role;

    public ProjectAssignmentDTO(ProjectDTO projectDTO, String userId, Role role){
        this.projectDTO = projectDTO;
        this.role=role;
    }
    public ProjectDTO getProjectDTO(){return projectDTO;}
    public String getUserId(){return userId;}
    public Role getRole(){return role;}

}
