package swengineering.team7.issuemanagementsystem.DTO;

public class IssueCountByTagDTO {
    private String tag;
    private Long count;

    public IssueCountByTagDTO(String tag, Long count) {
        this.tag = tag;
        this.count = count;
    }

    // Getters and Setters
    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Long getCount() {
        return count;
    }

    public void setCount(Long count) {
        this.count = count;
    }
}
