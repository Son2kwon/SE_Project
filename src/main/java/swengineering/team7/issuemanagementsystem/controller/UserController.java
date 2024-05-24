package swengineering.team7.issuemanagementsystem.controller;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import swengineering.team7.issuemanagementsystem.DTO.ProjectAssignmentDTO;
import swengineering.team7.issuemanagementsystem.DTO.UserInformationDTO;
import swengineering.team7.issuemanagementsystem.service.ProjectAssignmentService;
import swengineering.team7.issuemanagementsystem.service.UserService;
import swengineering.team7.issuemanagementsystem.util.JwtCertificate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class UserController {
    UserService userService;
    ProjectAssignmentService projectAssignmentService;

    @Autowired
    public UserController(UserService userService, ProjectAssignmentService projectAssignmentService) {

        this.userService = userService;
        this.projectAssignmentService = projectAssignmentService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> createUser(@RequestBody UserInformationDTO userInformationDTO) {
        userService.createUser(userInformationDTO.getId(), userInformationDTO.getPassword(),
                userInformationDTO.getName(), userInformationDTO.getContract());
        return ResponseEntity.status(HttpStatus.CREATED)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{\"message\": \"User created successfully\"}");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserInformationDTO userInformationDTO) {
        JSONObject responseJson = new JSONObject();
        if (userService.login(userInformationDTO)) {
            JwtCertificate jwtCertificate = new JwtCertificate();
            String token = jwtCertificate.generateToken(userInformationDTO.getId());
            responseJson.put("token", token);
            //테스트용, 일단 admin 박아둠
            if(userService.isAdmin(userInformationDTO))
                responseJson.put("role", "admin");
            return ResponseEntity.ok(responseJson.toString());
        } else {
            responseJson.put("message", "Invalid credentials");
            return ResponseEntity.status(401).body(responseJson.toString());
        }
    }
    @GetMapping("/getAllUser")
    public ResponseEntity<List<HashMap<String, String>>> getAllUser(@RequestParam("token") String token) {
        JwtCertificate jwtCertificate = new JwtCertificate();
        String id = jwtCertificate.extractId(token);
        List<HashMap<String, String>> userResponse = new ArrayList<>();
        List<UserInformationDTO> allUser = userService.getAllUser(jwtCertificate.extractId(token));
        for (UserInformationDTO user : allUser) {
            List<ProjectAssignmentDTO> assignments = projectAssignmentService.getAssignmentsByUserId(user.getId());
            boolean isAdmin = userService.isAdmin(user);
            if(assignments.isEmpty() || isAdmin){
                HashMap<String,String> noAssignmentMap = new HashMap<>();
                noAssignmentMap.put("id", user.getId());
                noAssignmentMap.put("name", user.getName());
                noAssignmentMap.put("charge", "");
                if(isAdmin) noAssignmentMap.put("role", "admin");
                else noAssignmentMap.put("role", "");
                userResponse.add(noAssignmentMap);
            }
            for (ProjectAssignmentDTO assignment : assignments) {
                HashMap<String, String> assignmentMap = new HashMap<>();
                assignmentMap.put("id", user.getId());
                assignmentMap.put("name", user.getName());
                assignmentMap.put("charge", assignment.getProjectDTO().getId().toString());
                assignmentMap.put("role", assignment.getRole().toString());
                userResponse.add(assignmentMap);
            }
        }
        return ResponseEntity.ok(userResponse);
    }
}