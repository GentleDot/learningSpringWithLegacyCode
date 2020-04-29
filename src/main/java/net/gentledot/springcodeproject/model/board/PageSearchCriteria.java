package net.gentledot.springcodeproject.model.board;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PageSearchCriteria extends PageCriteria {
    private String searchType;
    private String keyword;

    public PageSearchCriteria(Long page, Long perPageNum, String searchType, String keyword) {
        super(page, perPageNum);
        this.searchType = searchType;
        this.keyword = keyword;
    }

    public String getSearchType() {
        return searchType;
    }

    public String getKeyword() {
        return keyword;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("searchType", searchType)
                .append("keyword", keyword)
                .toString();
    }
}
