package swengineering.team7.issuemanagementsystem.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import swengineering.team7.issuemanagementsystem.DTO.IssueDTO;
import swengineering.team7.issuemanagementsystem.DTO.ProjectDTO;
import swengineering.team7.issuemanagementsystem.DTO.UserInformationDTO;
import swengineering.team7.issuemanagementsystem.entity.*;
import swengineering.team7.issuemanagementsystem.repository.IssueRepository;
import swengineering.team7.issuemanagementsystem.repository.ProjectAssignmentRepository;
import swengineering.team7.issuemanagementsystem.repository.ProjectRepository;
import swengineering.team7.issuemanagementsystem.repository.UserRepository;
import swengineering.team7.issuemanagementsystem.util.Priority;
import swengineering.team7.issuemanagementsystem.util.Role;
import swengineering.team7.issuemanagementsystem.util.State;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class IssueServiceTest {
    @Mock
    private IssueRepository issueRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private ProjectAssignmentRepository projectAssignmentRepository;

    @Mock
    private ProjectAssignmentService projectAssignmentService;

    @InjectMocks
    private IssueService issueService;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown(){}

    @Test
    void testCreateIssue() {
        IssueDTO issueDTO = new IssueDTO(1L,1L,"title",LocalDateTime.now(),State.NEW,"description",Priority.HIGH,"reporterid","reportername","#tag1");
        Tester tester = new Tester();
        tester.setId("reporterid");
        issueDTO.setProjectID(1L);
        issueDTO.setTitle("reporterid");

        Project project = new Project();
        project.setId(1L);
        Issue terminal_issue = Issue.makeIssueOf("title","description",LocalDateTime.now(),State.NEW,Priority.HIGH, "TestTag");
        terminal_issue.setReporter(tester);
        terminal_issue.setProject(project);

        when(userRepository.findById("reporterid")).thenReturn(Optional.of(tester));
        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(issueRepository.save(any(Issue.class))).thenReturn(terminal_issue);

        assertTrue(issueService.createIssue(issueDTO));
        verify(issueRepository, times(1)).save(any(Issue.class));
    }

    @Test
    void testUpdateIssue() {
        Set<String> A = new HashSet<>();
        A.add("assignee");
        IssueDTO issueDTO = new IssueDTO(1L,1L,"title", LocalDateTime.now(),State.CLOSED,"description", Priority.HIGH,"reporterid","reportername","#tag1#tag2#tag3","fixerid","fixername",A);
        Dev dev1 = new Dev();
        dev1.setId("fixerid");

        Issue terminal_issue = Issue.makeIssueOf("old_title","old_description",LocalDateTime.now(),State.FIXED,Priority.HIGH, "Testtag");
        terminal_issue.setId(1L);
        terminal_issue.setFixer(dev1);

        when(issueRepository.findById(1L)).thenReturn(Optional.of(terminal_issue));
        when(issueRepository.save(any(Issue.class))).thenReturn(terminal_issue);
        when(userRepository.findById("fixerid")).thenReturn(Optional.of(dev1));
        when(userRepository.save(any(User.class))).thenReturn(dev1);

        System.out.println(terminal_issue.getId());
        Boolean result = issueService.UpdateIssueInfo(issueDTO);

        assertTrue(result);
        assertEquals(1,dev1.getIssueResolve().get("tag1"));
        assertEquals(1,dev1.getIssueResolve().get("tag2"));
        assertEquals(1,dev1.getIssueResolve().get("tag3"));
        verify(issueRepository, times(2)).save(terminal_issue);
        verify(userRepository, times(1)).save(dev1);
    }

    @Test
    void testRecommendAssignee() {
        User dev1 = new User();
        List<String> dev_id = Arrays.asList("dev1","dev2","dev3","dev4");
        dev1.setId("dev1");

        User dev2 = new User();
        dev2.setId("dev2");
        dev2.getIssueResolve().put("tag1",1);
        dev2.getIssueResolve().put("tag2",1);
        dev2.getIssueResolve().put("tag3",1);

        User dev3 = new User();
        dev3.setId("dev3");
        dev3.getIssueResolve().put("tag1",2);
        dev3.getIssueResolve().put("tag2",2);
        dev3.getIssueResolve().put("tag3",2);

        User dev4 = new User();
        dev4.setId("dev4");
        dev4.getIssueResolve().put("tag1",3);
        dev4.getIssueResolve().put("tag2",3);
        dev4.getIssueResolve().put("tag3",3);

        // 반환되는 개발자 3명
        List<User> ans = new ArrayList<>();
        ans.add(dev4);
        ans.add(dev3);
        ans.add(dev2);

        // 임시 프로젝트 생성
        ProjectDTO projectDTO = new ProjectDTO(1L,"name",LocalDateTime.now(),LocalDateTime.now());
        Project project = new Project();
        project.setId(1L);
        project.setName("name");
        project.setStartDate(LocalDateTime.now());
        project.setDueDate(LocalDateTime.now());
        project.addAssignedUser(dev1);
        project.addAssignedUser(dev2);
        project.addAssignedUser(dev3);
        project.addAssignedUser(dev4);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(project));
        when(userRepository.findById("dev1")).thenReturn(Optional.of(dev1));
        when(userRepository.findById("dev2")).thenReturn(Optional.of(dev2));
        when(userRepository.findById("dev3")).thenReturn(Optional.of(dev3));
        when(userRepository.findById("dev4")).thenReturn(Optional.of(dev4));
        when(projectAssignmentService.getDevIdByProjectId(1L)).thenReturn(dev_id);
        List<UserInformationDTO> users = issueService.recommendAssignee(projectDTO.getId(),"#tag1#tag2#tag3");
       // for(int i=0;i<3;i++){
          //  assertEquals(ans.get(i).getId(),users.get(i).getId());
       // }

        for(int i=0;i<users.size();i++)
        {
            System.out.println(users.get(i).getId());
        }
    }

    @Test
    void testFindIssueByID() {
        Issue now = Issue.makeIssueOf("title", "description", LocalDateTime.now(), State.CLOSED, Priority.HIGH,"Testtag");
        now.setId(1L);

        Project p = new Project();
        p.setId(1L);
        now.setProject(p);

        User user = new User();
        user.setId("id1");
        now.setReporter(user);
        IssueDTO nowdto = IssueDTO.makeDTOFrom(now);

        IssueDTO target = new IssueDTO();
        target.setProjectID(1L);
        target.setId(1L);

        when(issueRepository.findById(1L)).thenReturn(Optional.of(now));
        List<IssueDTO> ans = issueService.findbyIssueID(1L);

        assertFalse(ans.isEmpty());
        assertEquals(ans.get(0).getId(),nowdto.getId());
    }

    @Test
    void setAssignees(){
        User u1 = new User();
        u1.setId("u1");

        User u2 = new User();
        u1.setId("u2");

        List<String> ids = Arrays.asList("u1","u2");

        Issue issue = new Issue();
        issue.setId(1L);

        when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));
        when(userRepository.findById("u1")).thenReturn(Optional.of(u1));
        when(userRepository.findById("u2")).thenReturn(Optional.of(u2));
        assertTrue(issueService.setAssignees(1L,ids));
    }
}
