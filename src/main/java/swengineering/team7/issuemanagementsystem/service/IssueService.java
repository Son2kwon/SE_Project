package swengineering.team7.issuemanagementsystem.service;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;


import swengineering.team7.issuemanagementsystem.DTO.ProjectDTO;
import swengineering.team7.issuemanagementsystem.DTO.SearchInfoDTO;
import swengineering.team7.issuemanagementsystem.DTO.UserInformationDTO;
import swengineering.team7.issuemanagementsystem.entity.*;
import swengineering.team7.issuemanagementsystem.repository.CommentRepository;
import swengineering.team7.issuemanagementsystem.DTO.IssueDTO;

import swengineering.team7.issuemanagementsystem.entity.Issue;
import swengineering.team7.issuemanagementsystem.entity.Project;
import swengineering.team7.issuemanagementsystem.entity.User;
import swengineering.team7.issuemanagementsystem.exception.WrongPriorityException;
import swengineering.team7.issuemanagementsystem.repository.IssueRepository;
import swengineering.team7.issuemanagementsystem.repository.ProjectRepository;
import swengineering.team7.issuemanagementsystem.repository.UserRepository;
import swengineering.team7.issuemanagementsystem.util.Priority;
import swengineering.team7.issuemanagementsystem.util.SearchType;
import swengineering.team7.issuemanagementsystem.util.State;


import java.util.*;

@Service
public class IssueService {

    private final UserService userService;
    IssueRepository issueRepository;
    UserRepository userRepository;
    ProjectRepository projectRepository;
    ProjectAssignmentService projectAssignmentService;

    public IssueService(UserService userService, IssueRepository issueRepository, UserRepository userRepository,
                        ProjectRepository projectRepository, ProjectAssignmentService projectAssignmentService) {
        this.userService = userService;
        this.issueRepository = issueRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
        this.projectAssignmentService = projectAssignmentService;
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
            issue.setState(State.ASSIGNED);
            return true;
        }
        return false;
    }
    //새로운 issue 하나를 만드는 작업 ( issue 저장 성공시 True 실패시 False 반환)
    public Boolean createIssue(IssueDTO issueDTO) {

        Issue newIssue = Issue.makeIssueOf(issueDTO.getTitle(), issueDTO.getIssueDescription(), issueDTO.getDate(), issueDTO.getState(), issueDTO.getPriority(), issueDTO.getTag());
        newIssue.setState(State.NEW);
        User user = userRepository.findById(issueDTO.getReporterID()).orElse(null);
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
        }else if (searchInfoDTO.getSearchType()==SearchType.ASSIGNEE) {
            return findByAssignee(searchInfoDTO.getSearchValue());
        }else if(searchInfoDTO.getSearchType()==SearchType.FIXER){
            return findByFixerID(searchInfoDTO.getSearchValue());
        }else if (searchInfoDTO.getSearchType() == SearchType.STATE) {
            return findbyState(searchInfoDTO.getSearchValue());
        } else if (searchInfoDTO.getSearchType() == SearchType.ISSUEID){
            return findbyIssueID(Long.parseLong(searchInfoDTO.getSearchValue()));
        } else if (searchInfoDTO.getSearchType() == SearchType.PRIORITY) {
            return findByPriority(searchInfoDTO.getSearchValue());
        }else if (searchInfoDTO.getSearchType() == SearchType.ALL){
            return findAll();
        }else {
            return null;
        }
    }

    public Set<String> getAssignedUsers(Set<User> assignedUsers){
        Set<String> result = new HashSet<>();
        for(User user: assignedUsers){
            result.add(user.getId());
        }
        return result;
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
    public List<IssueDTO> findByAssignee(String assigneeID){
        List<IssueDTO> issueDTOs = new ArrayList<>();
        List<Issue> issues = issueRepository.findByAssignedUserId(assigneeID);
        for(Issue issue:issues){
            issueDTOs.add(IssueDTO.makeDTOFrom(issue));
        }
        return issueDTOs;
    }
    public List<IssueDTO> findByFixerID(String fixerID){
        List<IssueDTO> issueDTOs = new ArrayList<>();
        List<Issue> issues = issueRepository.findByFixerId(fixerID);
        for(Issue issue:issues){
            issueDTOs.add(IssueDTO.makeDTOFrom(issue));
        }
        return issueDTOs;
    }

    public List<IssueDTO> findbyState(String state) {
        List<IssueDTO> issueDTOs = new ArrayList<>();
        State stateEnum = State.valueOf(state.toUpperCase());
        if(stateEnum instanceof State) {
            List<Issue> issues = issueRepository.findByStateOrderByDateDesc(stateEnum);
            for (Issue issue : issues) {
                issueDTOs.add(IssueDTO.makeDTOFrom(issue));
            }
        }
        return issueDTOs;
    }

    //Priority 기반 검색
    public List<IssueDTO> findByPriority(String priority) {
        // 문자열을 Priority Enum으로 변환
        Priority priorityEnum;
        try {
            priorityEnum = Priority.valueOf(priority.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new WrongPriorityException("Invalid priority value: " + priority);
        }

        // Priority Enum을 사용하여 검색
        List<Issue> issues = issueRepository.findByPriority(priorityEnum);
        List<IssueDTO> issueDTOs = new ArrayList<>();

        // 검색된 Issue 객체들을 IssueDTO로 변환
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
            issueDTOs.add(issue);
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
  
    public List<IssueDTO> selectByProjectID(List<IssueDTO> issues,Long projectId){
        List<IssueDTO> result = new ArrayList<>();
        for(IssueDTO issue: issues){
            if(Objects.equals(issue.getProjectID(), projectId)){
                result.add(issue);
            }
        }
        return result;
    }

    //특정 Issue 업데이트
    public Boolean UpdateIssueInfo(IssueDTO issueDTO) {
        Issue issue = issueRepository.findById(issueDTO.getId()).orElse(null);

        if (issue == null) {
            return false;
        }

        // 만약 issue의 상태가 closed, 즉 해결된 상태로 바뀐다면
        // 해당 issue에 배정된 Dev의 해결 이력 업데이트
        if(issueDTO.getState()==State.CLOSED) {
            for(User user : issue.getAssignedUsers()) {
                if(user instanceof Dev) {
                    //((Dev) user).incrementResolve(issueDTO.getTag());
                    userRepository.save(user);
                }
            }
        }
        issue.setTitle(issueDTO.getTitle());
        issue.setPriority(issueDTO.getPriority());
        issue.setIssueDescription(issueDTO.getIssueDescription());
        issueRepository.save(issue);
        //updateState(issueDTO);
        //무슨 함수인지??
        return true;
    }

    //State Update
    public Boolean updateState(IssueDTO issueDTO, String updaterID){
        Issue issue = issueRepository.findById(issueDTO.getId()).orElse(null);
        if(issue != null) {
            issue.setState(issueDTO.getState());
            if(issueDTO.getState()==State.FIXED && issueDTO.getReporterID() != null){
                //Fixer로 정하고, 해결 이력 업데이트하고 저장.
                User user = userRepository.findById(updaterID).orElse(null);
                if(user!=null){
                    issue.setFixer(user);
                    user.incrementResolve(issueDTO.getTag());
                    userRepository.save(user);
                }
            }
            issueRepository.save(issue);
            return true;
        }

       return false;
    }

    //Description 업데이트
    public Boolean updateDesciprtion(IssueDTO issueDTO){
        Issue issue = issueRepository.findById(issueDTO.getId()).orElse(null);
        if(issue != null) {
            String issueDescription = issueDTO.getIssueDescription();
            if(issueDTO.getIssueDescription()!=null){
                issue.setIssueDescription(issueDescription);
                issueRepository.save(issue);
            }
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

    public List<UserInformationDTO> recommendAssignee(Long projectID, String tag) {
        ///////////////////////////////////////////////////////////////////////
        // 최대 힙 구현을 위해 Comparable 메소드 오버라이딩
        class PriorityPair implements Comparable<PriorityPair> {
            private Integer priority;
            private User user;

            public PriorityPair(Integer priority, User user) {
                this.priority = priority;
                this.user = user;
            }

            public Integer getPriority() {
                return priority;
            }
            public User getUser() {
                return user;
            }

            @Override
            public int compareTo(PriorityPair o) {
                return o.priority.compareTo(this.priority);
            }
        }
        ///////////////////////////////////////////////////////////////////////
        // 태그가 빈 태그로 주어진다면, 추천기능 필요 x, 빈 리스트 반환
        if(tag.isEmpty()) {
            return new ArrayList<UserInformationDTO>();
        }
        String tagset[] = tag.split("#");
        List<String> temp_tagset = new ArrayList<>(Arrays.asList(tagset));
        temp_tagset.remove(0);
        tagset = temp_tagset.toArray(new String[temp_tagset.size()]);

        // temp 를 키값으로 가지는 우선순위큐 (Max Heap) 자료구조 생성
        PriorityQueue<PriorityPair> queue = new PriorityQueue<>();
        List<String> devIDs= projectAssignmentService.getDevIdByProjectId(projectID);
        Optional<Project> optionalProject = projectRepository.findById(projectID);
        if(optionalProject.isPresent()) {
            Project project = optionalProject.get();
            for(String devId: devIDs) {
                User user = userRepository.findById(devId).orElse(null);
                int temp=0;
                for(String s : tagset) {
                    if(user!=null && user.getIssueResolve().containsKey(s)){
                        temp=temp+user.getIssueResolve().get(s);
                    }
                }
                // 해결이력이 1개 이상 존재하고, Dev인경우 추가
                // Dev가 아니라면 애초에 해결이력이 늘어날 상황이 없기때문
                if(temp!=0) {
                    queue.offer(new PriorityPair(temp, user));
                }
            }
        }
        List<UserInformationDTO> ret = new ArrayList<>();
        // 상위 3명을 반환하거나, 프로젝트에 있는 Dev들이 모두 추가되기전까지 반복
        for(int i=0;i<3&&!queue.isEmpty();i++) {
            ret.add(UserInformationDTO.from(queue.poll().getUser()));
        }
        return ret;
    }
}
