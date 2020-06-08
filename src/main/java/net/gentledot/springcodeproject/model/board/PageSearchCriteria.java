package net.gentledot.springcodeproject.model.board;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.web.util.UriComponentsBuilder;

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

    public String getListLink() {
        UriComponentsBuilder queryParam = UriComponentsBuilder.fromPath("")
                .queryParam("page", this.getPage())
                .queryParam("perPageNum", this.getPerPageNum())
                .queryParam("searchType", this.searchType)
                .queryParam("keyword", this.keyword);

        return queryParam.toUriString();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("searchType", searchType)
                .append("keyword", keyword)
                .toString();
    }
}
