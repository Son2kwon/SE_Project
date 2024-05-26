package swengineering.team7.issuemanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import swengineering.team7.issuemanagementsystem.DTO.*;
import swengineering.team7.issuemanagementsystem.entity.Comment;
import swengineering.team7.issuemanagementsystem.entity.Issue;
import swengineering.team7.issuemanagementsystem.entity.Project;
import swengineering.team7.issuemanagementsystem.entity.User;
import swengineering.team7.issuemanagementsystem.repository.CommentRepository;
import swengineering.team7.issuemanagementsystem.DTO.IssueDTO;
import swengineering.team7.issuemanagementsystem.dto.SearchInfoDTO;
import swengineering.team7.issuemanagementsystem.entity.Issue;
import swengineering.team7.issuemanagementsystem.entity.Project;
import swengineering.team7.issuemanagementsystem.entity.User;
import swengineering.team7.issuemanagementsystem.exception.WrongPriorityException;
import swengineering.team7.issuemanagementsystem.repository.IssueRepository;
import swengineering.team7.issuemanagementsystem.repository.ProjectRepository;
import swengineering.team7.issuemanagementsystem.repository.UserRepository;
import swengineering.team7.issuemanagementsystem.util.Priority;
import swengineering.team7.issuemanagementsystem.util.SearchType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class IssueService {

    private final UserService userService;
    IssueRepository issueRepository;
    UserRepository userRepository;
    ProjectRepository projectRepository;
    CommentRepository commentRepository;

    public IssueService(UserRepository userRepository, IssueRepository issueRepository, ProjectRepository projectRepository, UserService userService) {
        this.userRepository = userRepository;
        this.issueRepository = issueRepository;
        this.projectRepository = projectRepository;
        this.userService = userService;
    }

    //새로운 issue 하나를 만드는 작업 ( issue 저장 성공시 True 실패시 False 반환)
    public Boolean createIssue(IssueDTO issueDTO) {
        Issue newIssue = Issue.makeIssueOf(issueDTO.getTitle(), issueDTO.getIssueDescription(), issueDTO.getDate(), issueDTO.getState());
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
        } else if (searchInfoDTO.getSearchType() == SearchType.STATE) {
            return findbyState(searchInfoDTO.getSearchValue());
        } else if (searchInfoDTO.getSearchType() == SearchType.ISSUEID){
            return findbyIssueID(Long.parseLong(searchInfoDTO.getSearchValue()));
        } else if (searchInfoDTO.getSearchType() == SearchType.PRIORITY){
            return findByPriority(searchInfoDTO.getSearchValue());
        }else if (searchInfoDTO.getSearchType() == SearchType.ALL){
            return findALl();
        }else {
            return null;
        }
    }

    public List<IssueDTO> findALl(){
        List<IssueDTO> issueDTOs = new ArrayList<>();
        List<Issue> issues = issueRepository.findAll();

        for (Issue issue : issues) {
            issueDTOs.add(IssueDTO.makeDTOFrom(issue));
        }

        return issueDTOs;
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
        List<Issue> issues = issueRepository.findByReporter_NameContainingOrderByDateDesc(writer);

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
        updateState(issueDTO);
        return true;
    }

    //State Update
    public Boolean updateState(IssueDTO issueDTO){
        Issue issue = issueRepository.findById(issueDTO.getId()).orElse(null);
        if(issue != null) {
            String issueState = issueDTO.getState();
            issue.setState(issueState);
            if(issueDTO.getState().equals("Complete") && issueDTO.getReporterID() != null){
                userRepository.findById(issueDTO.getReporterID()).ifPresent(issue::setFixer);
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

    public Boolean addComment(CommentDTO commentDTO, IssueDTO issueDTO) {
        Comment comment = Comment.makeCommentof(commentDTO.getBody(),commentDTO.getWriter(),commentDTO.getDate(),commentDTO.getIssue(),commentDTO.getUser());
        //올바른 comment 객체가 입력된경우
        if(commentDTO.getIssue() != null) {
            Issue issue = issueRepository.findById(issueDTO.getId()).orElse(null);
            //Comment 객체가 올바른  Issue에 추가되어야함
            if(issue != null && issue == comment.getIssue()) {
                issue.addComment(comment);
                issueRepository.save(issue);
                commentRepository.save(comment);
                return true;
            }
            else {
                return false;
            }
        }
        return false;
    }

    public Boolean modifyComment(CommentDTO dto, String content, LocalDateTime time) {
        Optional<Comment> C=commentRepository.findById(dto.getId());
        if(C.isPresent()) {
            Comment comment=C.get();
            comment.setBody(content);
            comment.setDate(time);
            this.commentRepository.save(comment);
            return true;
        }
        return false;
    }

    public Boolean deleteComment(CommentDTO dto) {
        Optional<Comment> C=commentRepository.findById(dto.getId());
        if(C.isPresent()) {
            Comment comment=C.get();
            Issue issue=comment.getIssue();
            User user=comment.getUser();
            issue.getComments().remove(comment);
            user.getComments().remove(comment);
            this.issueRepository.save(issue);
            this.commentRepository.delete(comment);
            this.userRepository.save(user);
            return true;
        }
        return false;
    }
}