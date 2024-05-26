package swengineering.team7.issuemanagementsystem.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import swengineering.team7.issuemanagementsystem.DTOs.ProjectAssignedUserDTO;
import swengineering.team7.issuemanagementsystem.DTOs.ProjectDTO;
import swengineering.team7.issuemanagementsystem.service.ProjectService;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class ProjectController {
    ProjectService projectService;

    @Autowired
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping("/createproject")
    public ResponseEntity<String> createProject(@RequestBody RequestData requestData){
        //token 인증 필요
        ProjectDTO projectDTO = new ProjectDTO();
        projectDTO.setName(requestData.getName()); projectDTO.setStartDate(requestData.getStartDate()); projectDTO.setDueDate(requestData.getDueDate());
        Long projectId = projectService.createProject(projectDTO);
        RelatedUser relatedUser = requestData.getRelatedUser();

        System.out.println("hib");
        List<String> userIds = new ArrayList<>();
        userIds.add(relatedUser.getPl()); userIds.addAll(relatedUser.getTester()); userIds.addAll(relatedUser.getDev());
        ProjectAssignedUserDTO projectAssignedUserDTO = new ProjectAssignedUserDTO(projectId,userIds);
        projectService.addAssignedUser(projectAssignedUserDTO);
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\": \"project created successfully\"}");
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