package swengineering.team7.issuemanagementsystem.service;

import org.springframework.stereotype.Service;
import swengineering.team7.issuemanagementsystem.DTO.ProjectAssignedUserDTO;
import swengineering.team7.issuemanagementsystem.DTO.ProjectDTO;
import swengineering.team7.issuemanagementsystem.entity.Project;
import swengineering.team7.issuemanagementsystem.entity.ProjectAssignment;
import swengineering.team7.issuemanagementsystem.entity.ProjectAssignmentKey;
import swengineering.team7.issuemanagementsystem.entity.User;
import swengineering.team7.issuemanagementsystem.repository.ProjectAssignmentRepository;
import swengineering.team7.issuemanagementsystem.repository.ProjectRepository;
import swengineering.team7.issuemanagementsystem.repository.UserRepository;
import swengineering.team7.issuemanagementsystem.util.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {

    ProjectRepository projectRepository;
    UserRepository userRepository;
    ProjectAssignmentRepository projectAssignmentRepository;

    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository, ProjectAssignmentRepository projectAssignmentRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
        this.projectAssignmentRepository = projectAssignmentRepository;
    }

    // 새로운 Project 생성
    public Long createProject(ProjectDTO projectDTO) {
        Project newProject = Project.makeProjectOf(projectDTO.getName(), projectDTO.getStartDate(),projectDTO.getDueDate());
        newProject.setStartDate(LocalDateTime.now());
        projectRepository.save(newProject);
        return newProject.getId();
    }

    // 모든 프로젝트 가져오기
    public List<ProjectDTO> getAllProjects() {
        List<Project> projects = projectRepository.findAllByOrderByStartDateDesc();
        List<ProjectDTO> projectDTOs = new ArrayList<>();
        for(Project project : projects) {
            projectDTOs.add(ProjectDTO.makeDTOFrom(project));
        }

        return projectDTOs;
    }

    // Project 정보 수정
    public Boolean updateProject(ProjectDTO projectDTO) {
        Project project = projectRepository.findById(projectDTO.getId()).orElse(null);
        if(project == null) {
            return false;
        }

        project.setName(projectDTO.getName());
        project.setDueDate(projectDTO.getDueDate());
        projectRepository.save(project);
        return true;
    }
    public boolean assignUserToProject(Long projectId, String userId, Role role) {
        Project project = projectRepository.findById(projectId).orElseThrow(() -> new RuntimeException("Project not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        ProjectAssignmentKey id = new ProjectAssignmentKey(projectId, userId);
        ProjectAssignment assignment = new ProjectAssignment();
        assignment.setId(id);
        assignment.setProject(project);
        assignment.setUser(user);
        assignment.setRole(role);

        projectAssignmentRepository.save(assignment);
        return true;
    }

    // Project에 유저 배정하기
    public Boolean addAssignedUser(ProjectAssignedUserDTO projectAssignedUserDTO) {

        Project project = projectRepository.findById(projectAssignedUserDTO.getProjectId()).orElse(null);
        if(project == null) {
            return false;
        }

        for(String userId : projectAssignedUserDTO.getAssignedUsersID()){
            User user=userRepository.findById(userId).orElse(null);
            if(user!=null){
                project.addAssignedUser(user);
                user.addinchargeProject(project);
            }
        }
        projectRepository.save(project);

        return true;
    }

    //Project 삭제
    public Boolean deleteProject(ProjectDTO projectDTO) {
        Project choiceProject = projectRepository.findById(projectDTO.getId()).orElse(null);
        if(choiceProject == null) {
            return false;
        }

        for (User user : choiceProject.getAssignedUsers()) {
            user.removeinchargeProjects(choiceProject);
        }

        projectRepository.delete(choiceProject);
        return true;
    }

}
