package swengineering.team7.issuemanagementsystem.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import swengineering.team7.issuemanagementsystem.DTO.CommentDTO;
import swengineering.team7.issuemanagementsystem.DTO.IssueDTO;
import swengineering.team7.issuemanagementsystem.entity.Comment;
import swengineering.team7.issuemanagementsystem.entity.Issue;
import swengineering.team7.issuemanagementsystem.entity.User;
import swengineering.team7.issuemanagementsystem.repository.CommentRepository;
import swengineering.team7.issuemanagementsystem.repository.IssueRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceTest {
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private IssueRepository issueRepository;

    @InjectMocks
    private CommentService commentService;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown(){}

    @Test
    void testaddComment() {
        Issue issue = new Issue();
        issue.setId(1L);
        User user = new User();

        CommentDTO commentDTO = new CommentDTO(1L,"body","writer", LocalDateTime.now(),issue,user);
        IssueDTO issueDTO = new IssueDTO();
        issueDTO.setId(1L);

        Comment terminal_comment = Comment.makeCommentof("body","writer",LocalDateTime.now(),issue,user);

        when(issueRepository.findById(1L)).thenReturn(Optional.of(issue));
        when(commentRepository.save(any(Comment.class))).thenReturn(terminal_comment);
        when(issueRepository.save(any(Issue.class))).thenReturn(issue);

        commentService.addComment(commentDTO,issueDTO);

        assertTrue(issue.getComments().contains(terminal_comment));
    }
}
