package swengineering.team7.issuemanagementsystem.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import swengineering.team7.issuemanagementsystem.DTO.ProjectAssignmentDTO;
import swengineering.team7.issuemanagementsystem.entity.Project;
import swengineering.team7.issuemanagementsystem.entity.ProjectAssignment;
import swengineering.team7.issuemanagementsystem.entity.ProjectAssignmentKey;
import swengineering.team7.issuemanagementsystem.entity.User;
import swengineering.team7.issuemanagementsystem.repository.ProjectAssignmentRepository;
import swengineering.team7.issuemanagementsystem.util.Role;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectAssignmentServiceTest {

    @Mock
    private ProjectAssignmentRepository projectAssignmentRepository;

    @Mock
    private User user;

    @InjectMocks
    private ProjectAssignmentService projectAssignmentService;

    @Test
    void getAssignmentsByUserId() {
        // Given
        String userId = "user1";
        Project project = new Project();
        project.setId(1L);
        project.setName("Project1");
        project.setStartDate(LocalDateTime.now());
        project.setDueDate(LocalDateTime.now().plusDays(30));

        ProjectAssignment projectAssignment = new ProjectAssignment();
        projectAssignment.setId(new ProjectAssignmentKey(1L, userId));
        projectAssignment.setProject(project);
        projectAssignment.setUser(user); // Using the mocked User object
        projectAssignment.setRole(Role.DEV);

        // Mock Object들의 메소드 반환값 설정
        when(user.getId()).thenReturn(userId);

        when(projectAssignmentRepository.findByIdUserId(userId)).thenReturn(Arrays.asList(projectAssignment));

        // When
        List<ProjectAssignmentDTO> result = projectAssignmentService.getAssignmentsByUserId(userId);

        // Then (결과 비교)
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Project1", result.get(0).getProjectDTO().getName());
        assertEquals(userId, result.get(0).getUserId());
        assertEquals(Role.DEV, result.get(0).getRole());

        verify(projectAssignmentRepository, times(1)).findByIdUserId(userId);
    }

    //Mock object를 이용하여 DevIdByProjectID()메소드가 올바르게 동작하는 지 확인
    @Test
    void getDevIdByProjectId() {
        // Given (초기 설정)
        Long projectId = 1L;
        String userId1 = "user1";
        String userId2 = "user2";

        Project project = new Project();
        project.setId(projectId);

        ProjectAssignment assignment1 = new ProjectAssignment();
        assignment1.setProject(project);
        assignment1.setUser(user); // Mock User Class 이용
        assignment1.setRole(Role.DEV);

        ProjectAssignment assignment2 = new ProjectAssignment();
        assignment2.setProject(project);
        assignment2.setUser(user); // Mock User Class 이용
        assignment2.setRole(Role.DEV);

        // Mock 객체의 메소드 반환값 설정
        // 첫번째 호출에는 userId1 을 두번째 호출에는 userId2를 반환
        when(user.getId()).thenReturn(userId1, userId2);

        // findByProjectIdAndRole 이 호출 되었을때 assignment1과 assignment2 배열을 반환
        when(projectAssignmentRepository.findByProjectIdAndRole(projectId, Role.DEV))
                .thenReturn(Arrays.asList(assignment1, assignment2));

        // When
        List<String> result = projectAssignmentService.getDevIdByProjectId(projectId);

        // Then
        System.out.println("Result 결과 :" +  result);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(userId1, result.get(0));
        assertEquals(userId2, result.get(1));

        verify(projectAssignmentRepository, times(1)).findByProjectIdAndRole(projectId, Role.DEV);
    }
}