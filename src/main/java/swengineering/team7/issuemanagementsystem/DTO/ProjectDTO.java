package swengineering.team7.issuemanagementsystem.DTO;

import swengineering.team7.issuemanagementsystem.entity.Project;

import java.time.LocalDateTime;

public class ProjectDTO {

    private Long id;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;

    public ProjectDTO() {
    }

    public ProjectDTO(Long id, String name, LocalDateTime startDate, LocalDateTime dueDate) {
        this.id = id;
        this.name = name;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }

    public static ProjectDTO makeDTOFrom(Project project){
        return new ProjectDTO(project.getId(), project.getName(), project.getStartDate(), project.getDueDate());
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public LocalDateTime getDueDate() {
        return dueDate;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public void setDueDate(LocalDateTime dueDate) {
        this.dueDate = dueDate;
    }
}