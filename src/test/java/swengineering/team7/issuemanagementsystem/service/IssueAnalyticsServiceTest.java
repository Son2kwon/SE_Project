package swengineering.team7.issuemanagementsystem.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import swengineering.team7.issuemanagementsystem.DTO.IssueCountByDateDTO;
import swengineering.team7.issuemanagementsystem.DTO.IssueCountByTagDTO;
import swengineering.team7.issuemanagementsystem.repository.IssueRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class IssueAnalyticsServiceTest {

    @Mock
    private IssueRepository issueRepository;

    @InjectMocks
    private IssueAnalyticsService issueAnalyticsService;

    @BeforeEach
    void setUp() {}

    @AfterEach
    void tearDown(){}

    //Mock Object를 이용하여 IssueCountByDate()가 올바르게 동작하는 지 확인
    @Test
    void testIssueCountsByDateWithMock() {
        //given
        // Mock Object 내용 (DB가 반환할걸로 예상되는 결과)
        List<Object[]> mockResult = Arrays.asList(
                new Object[]{java.sql.Date.valueOf(LocalDate.of(2024, 5, 25)), 5L},
                new Object[]{java.sql.Date.valueOf(LocalDate.of(2024, 5, 26)), 3L}
        );

        //Mock Object를 반환하는 타이밍 설정
        when(issueRepository.countIssuesByDate()).thenReturn(mockResult);

        //when (Test 대상 메소드 호출)
        List<IssueCountByDateDTO> issueCountsByDate = issueAnalyticsService.getIssueCountsByDate();

        //then (결과 비교)
        assertEquals(2, issueCountsByDate.size());
        assertEquals(LocalDate.of(2024, 5, 25), issueCountsByDate.get(0).getDate());
        assertEquals(5L, issueCountsByDate.get(0).getCount());
        assertEquals(LocalDate.of(2024, 5, 26), issueCountsByDate.get(1).getDate());
        assertEquals(3L, issueCountsByDate.get(1).getCount());
    }

    @Test
    void testIssueCountsByTagWithMock() {

        // given
        // Mock Object 내용 (DB가 반환할걸로 예상되는 결과)
        List<Object[]> mockResult = Arrays.asList(
                new Object[]{"bug", 10L},
                new Object[]{"feature", 7L}
        );

        //Mock Object를 반환하는 타이밍 설정
        when(issueRepository.countIssuesByTag()).thenReturn(mockResult);

        //When(Test 대상 메소드 호출)
        List<IssueCountByTagDTO> issueCountsByTag = issueAnalyticsService.getIssueCountsByTag();

        //then(결과 비교)
        assertEquals(2, issueCountsByTag.size());
        assertEquals("bug", issueCountsByTag.get(0).getTag());
        assertEquals(10L, issueCountsByTag.get(0).getCount());
        assertEquals("feature", issueCountsByTag.get(1).getTag());
        assertEquals(7L, issueCountsByTag.get(1).getCount());
    }
}