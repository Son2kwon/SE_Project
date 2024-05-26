package swengineering.team7.issuemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import swengineering.team7.issuemanagementsystem.DTO.ProjectAssignedUserDTO;
import swengineering.team7.issuemanagementsystem.DTO.ProjectAssignmentDTO;
import swengineering.team7.issuemanagementsystem.DTO.ProjectDTO;
import swengineering.team7.issuemanagementsystem.service.ProjectAssignmentService;
import swengineering.team7.issuemanagementsystem.service.ProjectService;
import swengineering.team7.issuemanagementsystem.service.UserService;
import swengineering.team7.issuemanagementsystem.util.JwtCertificate;
import swengineering.team7.issuemanagementsystem.util.Role;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class ProjectController {
    ProjectService projectService;
    ProjectAssignmentService projectAssignmentService;

    @Autowired
    public ProjectController(ProjectService projectService, ProjectAssignmentService projectAssignmentService) {
        this.projectService = projectService;
        this.projectAssignmentService = projectAssignmentService;
    }

    @PostMapping("/createproject")
    public ResponseEntity<String> createProject(@RequestBody RequestData requestData){
        //token 인증 필요
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName(requestData.getName()); projectDTO.setStartDate(requestData.getStartDate()); projectDTO.setDueDate(requestData.getDueDate());
        Long projectId = projectService.createProject(projectDTO);
        RelatedUser relatedUser = requestData.getRelatedUser();

        projectService.assignUserToProject(projectId, relatedUser.getPl(), Role.PL);
        for(String testerId: relatedUser.getTester()){
            projectService.assignUserToProject(projectId, testerId, Role.TESTER);
        }
        for(String devId: relatedUser.getDev()){
            projectService.assignUserToProject(projectId, devId, Role.DEV);
        }

        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\": \"project created successfully\"}");
    }

    @GetMapping("/getProjectList")
    public ResponseEntity<List<HashMap<String,String>>> getProject(@RequestParam("token") String token) {
        JwtCertificate jwtCertificate = new JwtCertificate();
        String userId = jwtCertificate.extractId(token);
        List<ProjectAssignmentDTO> assignments = projectAssignmentService.getAssignmentsByUserId(userId);

        List<HashMap<String,String>> response = new ArrayList<>();
        for(ProjectAssignmentDTO projectAssignmentDTO: assignments){
            HashMap<String,String> projectInfo = new HashMap<>();
            projectInfo.put("id",projectAssignmentDTO.getProjectDTO().getId().toString());
            projectInfo.put("name",projectAssignmentDTO.getProjectDTO().getName());
            response.add(projectInfo);
        }
        return ResponseEntity.ok(response);
    }
}

class RequestData {
    private String token;
    private String name;
    private LocalDateTime startDate;
    private LocalDateTime dueDate;
    private RelatedUser relatedUser;

    public RequestData() {}
    public String getName() {return name;}
    public String getToken() {return token;}
    public LocalDateTime getStartDate() {return startDate;}
    public LocalDateTime getDueDate() {return dueDate;}
    public RelatedUser getRelatedUser() {return relatedUser;}
    public void setRelatedUser(RelatedUser relatedUser) {this.relatedUser = relatedUser;}

}

class RelatedUser {
    private String pl;
    private List<String> tester;
    private List<String> dev;

    public RelatedUser() {}
    public String getPl() {return pl;}
    public List<String> getTester() {return tester;}
    public List<String> getDev() {return dev;}
}