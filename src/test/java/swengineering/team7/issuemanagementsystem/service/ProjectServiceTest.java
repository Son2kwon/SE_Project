package swengineering.team7.issuemanagementsystem.service;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import swengineering.team7.issuemanagementsystem.DTO.ProjectDTO;
import swengineering.team7.issuemanagementsystem.repository.ProjectRepository;
import swengineering.team7.issuemanagementsystem.repository.UserRepository;
import swengineering.team7.issuemanagementsystem.entity.Project;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private ProjectService projectService;

    @BeforeEach
    void setUp() {}

    @Test
    void testCreateProject() {
        // given
        ProjectDTO projectDTO = new ProjectDTO(null, "Test Project", LocalDateTime.now(), LocalDateTime.now().plusDays(30));

        //Project Repository의 save 메소드를 호출할 때 반환할 프로젝트 설정 (Mock 객체)
        Project savedProject = Project.makeProjectOf("Test Project", projectDTO.getStartDate(), projectDTO.getDueDate());
        savedProject.setId(1L);

        // 아무 Project Type의 객체로 save를 호출하는 경우 savedProject Mock object를 반환
        when(projectRepository.save(any(Project.class))).thenReturn(savedProject);

        // when
        Long projectId = projectService.createProject(projectDTO);

        // then
        assertEquals(1L, projectId);
        // ProjectRepository로 save 메소드가 정확히 1번 호출되었는가?
        verify(projectRepository, times(1)).save(any(Project.class));
    }
}