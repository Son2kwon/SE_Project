package swengineering.team7.issuemanagementsystem.controller;

import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import swengineering.team7.issuemanagementsystem.DTO.*;
import swengineering.team7.issuemanagementsystem.service.IssueAnalyticsService;
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
    IssueAnalyticsService issueAnalyticsService;
    @Autowired
    public IssueController(UserService userService, IssueService issueService, IssueAnalyticsService issueAnalyticsService) {
        this.userService = userService;
        this.issueService = issueService;
        this.issueAnalyticsService=issueAnalyticsService;
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
        issueDTO.setTag((String)payload.get("tag"));

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
        List<IssueDTO> searchResult;

        switch(lowerRoute) {
            default:
                searchResult = new ArrayList<>();
                break;
            case "byIssueStatus":
                searchInfoDTO = new SearchInfoDTO(status, SearchType.STATE);
                searchResult = issueService.searchIssueInfo(searchInfoDTO);
                break;
            case "byPerson":
                switch(role){
                    default:
                    case "reporter":
                        searchInfoDTO = new SearchInfoDTO(userId, SearchType.WRITER);
                        break;
                    case "assignee":
                        searchInfoDTO = new SearchInfoDTO(userId, SearchType.ASSIGNEE);
                        break;
                    case "fixer":
                        searchInfoDTO = new SearchInfoDTO(userId, SearchType.FIXER);
                        break;
                }
                searchResult = issueService.searchIssueInfo(searchInfoDTO);
                break;
            case "byPriority":
                searchResult = issueService.findbyPriority(Priority.valueOf(priority));
                break;
            case "all":
                searchResult = issueService.findAll();
                break;
        }
        searchResult = issueService.selectByProjectID(searchResult,Long.parseLong(projectId));
        List<HashMap<String,String>> response = new ArrayList<>();


        for(IssueDTO issueDTO: searchResult){
            HashMap<String,String> tmp = new HashMap<>();
            tmp.put("id",issueDTO.getId().toString());
            tmp.put("title",issueDTO.getTitle());
            tmp.put("status",issueDTO.getState().toString());
            tmp.put("priority",issueDTO.getPriority().toString());
            tmp.put("date",issueDTO.getDate().toString());
            tmp.put("reporter",issueDTO.getReporterID());
            tmp.put("fixer",issueDTO.getFixer());
            tmp.put("tag",issueDTO.getTag());
            if(issueDTO.getAssignees()!=null){
                tmp.put("assignees",String.join(", ", issueDTO.getAssignees()));
            }else{
                tmp.put("assignees","");
            }
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
    @GetMapping("/getIssueCountByDate")
    public ResponseEntity<List<Map<String,String>>> getIssueCountByDate(){
        List<Map<String,String>> response = new ArrayList<>();
        List<IssueCountByDateDTO> result = issueAnalyticsService.getIssueCountsByDate();
        for(IssueCountByDateDTO r: result){
            Map<String,String> map = new HashMap<>();
            map.put("date",r.getDate().toString());
            map.put("count",r.getCount().toString());
            response.add(map);
        }
        return ResponseEntity.ok(response);
    }
    @GetMapping("/getIssueCountByTag")
    public ResponseEntity<List<IssueCountByTagDTO>> getIssueCountByTag(){
        return ResponseEntity.ok(issueAnalyticsService.getIssueCountsByTag());
    }
    @GetMapping("/getRecommendDev")
    public ResponseEntity<List<String>> getRecommendUser(
            @RequestParam("projectID") String projectID,
            @RequestParam("tag") String tag
    ){
        List<UserInformationDTO> users = issueService.recommendAssignee(Long.parseLong(projectID),tag);
        List<String> response = new ArrayList<>();
        for(UserInformationDTO user:users){response.add(user.getId());}
        return ResponseEntity.ok(response);
    }
}