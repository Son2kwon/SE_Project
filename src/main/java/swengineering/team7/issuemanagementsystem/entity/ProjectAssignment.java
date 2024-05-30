package swengineering.team7.issuemanagementsystem.entity;

import jakarta.persistence.*;
import swengineering.team7.issuemanagementsystem.util.Role;

@Entity
public class ProjectAssignment {

    @EmbeddedId
    private ProjectAssignmentKey id;

    @ManyToOne
    @MapsId("projectId")
    @JoinColumn(name = "project_id")
    private Project project;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @Enumerated(EnumType.STRING)
    private Role role;

    // Getters and setters
    public void setId(ProjectAssignmentKey id){this.id=id;}
    public ProjectAssignmentKey getId(){return id;}

    public void setProject(Project project) {this.project = project;}
    public Project getProject() {return project;}

    public void setUser(User user) {this.user = user;}
    public User getUser() {return user;}

    public void setRole(Role role) {this.role = role;}
    public Role getRole() {return role;}
}