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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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

    @Test
    void GetAllCommentsByIssueID() {
        Comment cmt1 = new Comment();
        cmt1.setId(1L);
        cmt1.setDate(LocalDateTime.of(2024,5,31,9,0));

        Comment cmt2 = new Comment();
        cmt2.setId(2L);
        cmt2.setDate(LocalDateTime.of(2024,5,31,10,0));

        Comment cmt3 = new Comment();
        cmt3.setId(3L);
        cmt3.setDate(LocalDateTime.of(2024,5,31,11,0));

        Issue target = new Issue();
        target.setId(1L);

        List<Comment> ans = new ArrayList<>();
        ans.add(cmt1);
        ans.add(cmt2);
        ans.add(cmt3);

        when(issueRepository.findById(1L)).thenReturn(Optional.of(target));
        when(commentRepository.findByIssueOrderByDateAsc(Optional.of(target))).thenReturn(ans);
        List<CommentDTO> tmp = commentService.getAllCommentsByIssueID(1L);
        for(int i=0;i<3;i++){
            assertEquals(i+1,tmp.get(i).getId());
        }
    }

}
