package swengineering.team7.issuemanagementsystem.DTO;

import swengineering.team7.issuemanagementsystem.util.SearchType;

public class SearchInfoDTO {
    // 검색하는 종류( ex) 제목, 작성자 이름, state)
    private SearchType searchType;
    // 유저가 입력한 정보
    private String searchValue;

    public SearchInfoDTO() {}

    public SearchInfoDTO(SearchType searchType, String searchValue) {
        this.searchType = searchType;
        this.searchValue = searchValue;
    }

    public SearchType getSearchType() {
        return searchType;
    }

    public String getSearchValue() {
        return searchValue;
    }

}
