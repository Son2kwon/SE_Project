package swengineering.team7.issuemanagementsystem.controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import swengineering.team7.issuemanagementsystem.DTO.IssueDTO;
import swengineering.team7.issuemanagementsystem.DTO.SearchInfoDTO;
import swengineering.team7.issuemanagementsystem.service.IssueService;
import swengineering.team7.issuemanagementsystem.service.UserService;
import swengineering.team7.issuemanagementsystem.util.JwtCertificate;
import swengineering.team7.issuemanagementsystem.util.Priority;
import swengineering.team7.issuemanagementsystem.util.SearchType;

import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin(origins = "http://localhost:3000")
@Controller
public class IssueController {
    IssueService issueService;
    UserService userService;
    @Autowired
    public IssueController(UserService userService, IssueService issueService) {

        this.userService = userService;
        this.issueService = issueService;
    }
    @PostMapping("/createIssue")
    public ResponseEntity<Void> create(@RequestBody Map<String, Object> payload){
        JwtCertificate jwtCertificate = new JwtCertificate();
        String id = jwtCertificate.extractId((String) payload.get("token"));

        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setTitle((String) payload.get("title"));
        issueDTO.setIssueDescription((String) payload.get("issueDescription"));
        issueDTO.setPriority(Priority.valueOf((String) payload.get("priority")));
        issueDTO.setDate(LocalDateTime.now());
        issueDTO.setReporterID(id);
        issueDTO.setProjectID(Long.parseLong((String) payload.get("projectId")));

        if(issueService.createIssue(issueDTO)){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    @GetMapping("/search/{lowerRoute}")
    public ResponseEntity<List<HashMap<String,String>>> searchIssue(
            @PathVariable String lowerRoute,
            @RequestParam("token") String token,
            @RequestParam("projectId") String projectId,
            @RequestParam(required = false, value="status") String status,
            @RequestParam(required = false, value="role") String role,
            @RequestParam(required = false, value="id") String userId,
            @RequestParam(required = false, value="priority") String priority){
        SearchInfoDTO searchInfoDTO;
        List<IssueDTO> searchResult = new ArrayList<>();

        switch(lowerRoute) {
            case "byIssueStatus":
                searchInfoDTO = new SearchInfoDTO(status, SearchType.STATE);
                searchResult = issueService.searchIssueInfo(searchInfoDTO);
                break;
            case "byPerson":
                searchInfoDTO = new SearchInfoDTO(userId, SearchType.WRITER);
                searchResult = issueService.searchIssueInfo(searchInfoDTO);
                break;
            case "byPriority":
                searchResult = issueService.findbyPriority(Priority.valueOf(priority));
                break;
            case "all":
                searchResult = issueService.findAll();
                break;
        }
        List<HashMap<String,String>> response = new ArrayList<>();

        for(IssueDTO issue: searchResult){
            HashMap<String,String> tmp = new HashMap<>();
            tmp.put("id",issue.getId().toString());
            tmp.put("title",issue.getTitle());
            tmp.put("status",issue.getState());
            tmp.put("priority",issue.getPriority().toString());
            tmp.put("date",issue.getDate().toString());
            tmp.put("reporter",issue.getReporterID());
            tmp.put("fixer",issue.getFixer());
            response.add(tmp);
        }
        return ResponseEntity.ok(response);
    }
    @PostMapping("/assignDev")
    public ResponseEntity<Void> assignDev(@RequestBody Map<String, Object> payload){
        //body = {assignees: [{issueId: '3', assignee: '5'}, ...]}
        String token = (String) payload.get("token");
        String projectId = (String) payload.get("projectId");
        List<HashMap<String,String>> assigneeList = (List<HashMap<String,String>>) payload.get("assignees");

        HashMap<Long,List<String>> assigneeMap = new HashMap<>();
        for(HashMap<String,String> assignee: assigneeList) {
            String issueIdNumber = assignee.get("issueId");
            List<String> issueIdMap = assigneeMap.getOrDefault(Long.parseLong(assignee.get("issueId")),new ArrayList<>());
            issueIdMap.add(assignee.get("assignee"));
            assigneeMap.put(Long.parseLong(assignee.get("issueId")),issueIdMap);
        }
        Set<Map.Entry<Long, List<String>>> entrySet = assigneeMap.entrySet();

        for (Map.Entry<Long, List<String>> entry : entrySet) {
            Long key = entry.getKey();
            List<String> value = entry.getValue();
            if(issueService.setAssignees(key,value)){
            }else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            }
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}