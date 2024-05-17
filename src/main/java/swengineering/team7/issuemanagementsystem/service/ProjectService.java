package swengineering.team7.issuemanagementsystem.service;

import org.springframework.stereotype.Service;
import swengineering.team7.issuemanagementsystem.dto.ProjectAssignedUserDTO;
import swengineering.team7.issuemanagementsystem.dto.ProjectDTO;
import swengineering.team7.issuemanagementsystem.entitiy.Project;
import swengineering.team7.issuemanagementsystem.entitiy.User;
import swengineering.team7.issuemanagementsystem.repository.ProjectRepository;
import swengineering.team7.issuemanagementsystem.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {

    ProjectRepository projectRepository;
    UserRepository userRepository;

    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    // 새로운 Project 생성
    public Boolean createProject(ProjectDTO projectDTO) {
        Project newProject = Project.makeProjectOf(projectDTO.getName(), projectDTO.getStartDate(),projectDTO.getDueDate());
        newProject.setStartDate(LocalDateTime.now());
        projectRepository.save(newProject);
        return true;
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

    // Project에 유저 배정하기
    public Boolean addAssignedUser(ProjectAssignedUserDTO projectAssignedUserDTO) {

        Project project = projectRepository.findById(projectAssignedUserDTO.getProjectId()).orElse(null);
        if(project == null) {
            return false;
        }

        for(Long userId : projectAssignedUserDTO.getAssignedUsersID()){
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
