package swengineering.team7.issuemanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swengineering.team7.issuemanagementsystem.DTO.ProjectAssignedUserDTO;
import swengineering.team7.issuemanagementsystem.DTO.ProjectAssignmentDTO;
import swengineering.team7.issuemanagementsystem.DTO.ProjectDTO;
import swengineering.team7.issuemanagementsystem.entity.ProjectAssignment;
import swengineering.team7.issuemanagementsystem.repository.ProjectAssignmentRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectAssignmentService {

    @Autowired
    private final ProjectAssignmentRepository projectAssignmentRepository;
    public ProjectAssignmentService(ProjectAssignmentRepository projectAssignmentRepository){
        this.projectAssignmentRepository=projectAssignmentRepository;
    }
    public List<ProjectAssignmentDTO> getAssignmentsByUserId(String userId) {
        List<ProjectAssignmentDTO> result = new ArrayList<>();
        for(ProjectAssignment pa: projectAssignmentRepository.findByIdUserId(userId)){
            ProjectDTO projectDTO = ProjectDTO.makeDTOFrom(pa.getProject());
            ProjectAssignmentDTO dto = new ProjectAssignmentDTO(projectDTO, pa.getUser().getId(), pa.getRole());
            result.add(dto);
        }
        return result;
    }
}
