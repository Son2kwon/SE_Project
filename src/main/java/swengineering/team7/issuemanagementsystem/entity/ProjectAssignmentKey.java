package swengineering.team7.issuemanagementsystem.entity;
import java.io.Serializable;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ProjectAssignmentKey implements Serializable {

    @Column(name = "project_id")
    private Long projectId;

    @Column(name = "user_id")
    private String userId;

    public ProjectAssignmentKey() {}

    public ProjectAssignmentKey(Long projectId, String userId) {
        this.projectId = projectId;
        this.userId = userId;
    }

    public Long getProjectId(){return projectId;}
    public String getUserId(){return userId;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ProjectAssignmentKey that = (ProjectAssignmentKey) o;
        return Objects.equals(projectId, that.projectId) &&
                Objects.equals(userId, that.userId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(projectId, userId);
    }
}