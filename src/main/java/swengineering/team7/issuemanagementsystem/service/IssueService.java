package swengineering.team7.issuemanagementsystem.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import swengineering.team7.issuemanagementsystem.DTO.IssueDTO;
import swengineering.team7.issuemanagementsystem.DTO.SearchInfoDTO;
import swengineering.team7.issuemanagementsystem.DTO.UserInformationDTO;
import swengineering.team7.issuemanagementsystem.entity.Issue;
import swengineering.team7.issuemanagementsystem.entity.Project;
import swengineering.team7.issuemanagementsystem.entity.User;
import swengineering.team7.issuemanagementsystem.repository.IssueRepository;
import swengineering.team7.issuemanagementsystem.repository.ProjectRepository;
import swengineering.team7.issuemanagementsystem.repository.UserRepository;
import swengineering.team7.issuemanagementsystem.util.Priority;
import swengineering.team7.issuemanagementsystem.util.SearchType;


import java.util.*;

@Service
public class IssueService {

    private final UserService userService;
    IssueRepository issueRepository;
    UserRepository userRepository;
    ProjectRepository projectRepository;

    public IssueService(UserRepository userRepository, IssueRepository issueRepository, ProjectRepository projectRepository, UserService userService) {
        this.userRepository = userRepository;
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
        this.userService = userService;
    }
    @Transactional
    public Boolean setAssignees(Long issueId, List<String> userIds){
        Issue issue = issueRepository.findById(issueId).orElse(null);
        Set<User> assignees = new HashSet<>();
        for(String userId: userIds){
            User user = userRepository.findById(userId).orElse(null);
            assignees.add(user);
        }
        if(issue!=null && !assignees.isEmpty()) {
            issue.setAssignedUsers(assignees);
            issue.setState("Assigned");
            return true;
        }
        return false;
    }
    //새로운 issue 하나를 만드는 작업 ( issue 저장 성공시 True 실패시 False 반환)
    public Boolean createIssue(IssueDTO issueDTO) {
        Issue newIssue = Issue.makeIssueOf(issueDTO.getTitle(), issueDTO.getIssueDescription(), issueDTO.getDate(), issueDTO.getState(), issueDTO.getPriority());
        newIssue.setState("NEW");
        User user = userRepository.findById(issueDTO.getUserID()).orElse(null);
        Project project =  projectRepository.findById(issueDTO.getProjectID()).orElse(null);
        if (user == null || project == null) {
            return false;
        }
        newIssue.setReporter(user);
        user.addIssue(newIssue);
        newIssue.setProject(project);
        project.addIssue(newIssue);
        issueRepository.save(newIssue);
        return true;
    }

    //SearchType에 맞게 검색을 수행하는 메소드
    public List<IssueDTO> searchIssueInfo(SearchInfoDTO searchInfoDTO) {
        if (searchInfoDTO.getSearchType() == SearchType.TITLE) {
            return findbyTitle(searchInfoDTO.getSearchValue());
        } else if (searchInfoDTO.getSearchType() == SearchType.WRITER) {
            return findbyWriter(searchInfoDTO.getSearchValue());
        } else if (searchInfoDTO.getSearchType() == SearchType.STATE) {
            return findbyState(searchInfoDTO.getSearchValue());
        } else if (searchInfoDTO.getSearchType() == SearchType.IssueID){
            return findbyIssueID(Long.parseLong(searchInfoDTO.getSearchValue()));
        } else {
            return null;
        }
    }

    public List<IssueDTO> findbyTitle(String title) {
        List<IssueDTO> issueDTOs = new ArrayList<>();
        List<Issue> issues = issueRepository.findByTitleContainingOrderByDateDesc(title);

        for (Issue issue : issues) {
            issueDTOs.add(IssueDTO.makeDTOFrom(issue));
        }

        return issueDTOs;
    }

    public List<IssueDTO> findbyWriter(String writer) {
        List<IssueDTO> issueDTOs = new ArrayList<>();
        List<Issue> issues = issueRepository.findByReporter_usernameContainingOrderByDateDesc(writer);

        for (Issue issue : issues) {
            issueDTOs.add(IssueDTO.makeDTOFrom(issue));
        }

        return issueDTOs;
    }

    public List<IssueDTO> findbyState(String state) {
        List<IssueDTO> issueDTOs = new ArrayList<>();
        List<Issue> issues = issueRepository.findByStateContainingOrderByDateDesc(state);

        for (Issue issue : issues) {
            issueDTOs.add(IssueDTO.makeDTOFrom(issue));
        }

        return issueDTOs;
    }

    // 이슈 아이디를 통한 Issue 정보 얻기
    public List<IssueDTO> findbyIssueID(Long issueID) {
        List<IssueDTO> issueDTOs = new ArrayList<>();
        Issue findIssue = issueRepository.findById(issueID).orElse(null);
        if(findIssue != null) {
            IssueDTO issue = IssueDTO.makeDTOFrom(findIssue);
            issue.setProjectID(findIssue.getProject().getId());
        }

        return issueDTOs;
    }
    public List<IssueDTO> findbyPriority(Priority priority){
        List<IssueDTO> issueDTOs = new ArrayList<>();
        List<Issue> issues = issueRepository.findByPriority(priority);
        for (Issue issue : issues) {
            issueDTOs.add(IssueDTO.makeDTOFrom(issue));
        }
        return issueDTOs;
    }
    public List<IssueDTO> findAll(){
        List<IssueDTO> issueDTOs = new ArrayList<>();
        List<Issue> issues = issueRepository.findAll();
        for (Issue issue : issues) {
            issueDTOs.add(IssueDTO.makeDTOFrom(issue));
        }
        return issueDTOs;
    }
    //특정 Issue 업데이트
    public Boolean UpdateIssueInfo(IssueDTO issueDTO) {
        Issue issue = issueRepository.findById(issueDTO.getId()).orElse(null);

        if (issue == null) {
            return false;
        }

        issue.setTitle(issueDTO.getTitle());
        issue.setPriority(issueDTO.getPriority());
        issue.setIssueDescription(issueDTO.getIssueDescription());
        issueRepository.save(issue);
        UpdateState(issueDTO);
        return true;
    }

    public Boolean UpdateState(IssueDTO issueDTO){
        Issue issue = issueRepository.findById(issueDTO.getId()).orElse(null);
        if(issue != null) {
            String issueState = issueDTO.getState();
            issue.setState(issueState);
            if(issueDTO.getState().equals("Complete")){
                userRepository.findById(issueDTO.getUserID()).ifPresent(issue::setFixer);
            }
            issueRepository.save(issue);
            return true;
        }

       return false;
    }

    //특정 Issue 삭제
    public Boolean deleteIssue(IssueDTO issueDTO) {
        if (issueDTO.getId() != null) {
            Optional<Issue> optionalIssue = issueRepository.findById(issueDTO.getId());
            if (optionalIssue.isPresent()) {
                Issue issue = optionalIssue.get();
                User user = issue.getReporter(); // 해당 이슈를 생성한 유저
                Project project = issue.getProject(); // 해당 이슈의 프로젝트

                // Issue를 유저의 이슈 목록에서 제거
                if (user != null) {
                    user.removeIssue(issue);
                }
                if (project != null) {
                    project.removeIssue(issue);
                }

                issueRepository.deleteById(issueDTO.getId());
                return true;
            }
        }
        return false;

    }

}