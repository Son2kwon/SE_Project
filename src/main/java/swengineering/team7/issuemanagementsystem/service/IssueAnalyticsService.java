package swengineering.team7.issuemanagementsystem.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swengineering.team7.issuemanagementsystem.DTO.IssueCountByDateDTO;
import swengineering.team7.issuemanagementsystem.DTO.IssueCountByTagDTO;
import swengineering.team7.issuemanagementsystem.repository.IssueRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// 이슈 통계분석을 담당하는 Service
@Service
public class IssueAnalyticsService {

    private final IssueRepository issueRepository;

    @Autowired
    public IssueAnalyticsService(IssueRepository issueRepository) {
        this.issueRepository = issueRepository;
    }

    // 날짜별 이슈의 개수를 반환하는 메서드
    public List<IssueCountByDateDTO> getIssueCountsByDate() {
        List<Object[]> results = issueRepository.countIssuesByDate();
        List<IssueCountByDateDTO> issueCountsByDate = new ArrayList<>();

        for (Object[] result : results) {
            java.sql.Date sqlDate = (java.sql.Date) result[0];
            LocalDate date = sqlDate.toLocalDate();
            Long count = (Long) result[1];
            issueCountsByDate.add(new IssueCountByDateDTO(date, count));
        }

        return issueCountsByDate;
    }

    //태그 별 이슈의 개수를 반환하는 메서드
    public List<IssueCountByTagDTO> getIssueCountsByTag() {
        List<Object[]> results = issueRepository.countIssuesByTag();
        List<IssueCountByTagDTO> issueCountsByTag = new ArrayList<>();

        for (Object[] result : results) {
            String tag = (String) result[0];
            Long count = (Long) result[1];
            issueCountsByTag.add(new IssueCountByTagDTO(tag, count));
        }

        return issueCountsByTag;
    }


}



