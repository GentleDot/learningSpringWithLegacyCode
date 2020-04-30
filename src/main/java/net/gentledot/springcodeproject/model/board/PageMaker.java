package net.gentledot.springcodeproject.model.board;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

public class PageMaker {
    private long totalCount;
    private long startPage;
    private long endPage;
    private boolean prev;
    private boolean next;
    private long displayPageNum;
    private PageCriteria criteria;

    public PageMaker(PageCriteria criteria, Long totalCount) {
        checkNotNull(criteria, "페이징 설정 기준은 null이 될 수 없습니다.");
        checkArgument(totalCount != null && totalCount >= 1, "총 데이터의 수는 필수이며 음수가 될 수 없습니다.");

        this.criteria = criteria;
        this.totalCount = totalCount;
        displayPageNum = ObjectUtils.defaultIfNull(criteria.getPerPageNum(), 10L);
        calcData();
    }

    public Long getTotalCount() {
        return totalCount;
    }

    public Long getStartPage() {
        return startPage;
    }

    public Long getEndPage() {
        return endPage;
    }

    public boolean isPrev() {
        return prev;
    }

    public boolean isNext() {
        return next;
    }

    public Long getDisplayPageNum() {
        return displayPageNum;
    }

    public PageCriteria getCriteria() {
        return criteria;
    }

    public String makeQuery(Long page) {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .queryParam("page", page)
                .queryParam("perPageNum", displayPageNum)
                .build();

        return uri.toUriString();
    }

    public String makeSearch(Long page) {
        UriComponents uri = UriComponentsBuilder.newInstance()
                .queryParam("page", page)
                .queryParam("perPageNum", criteria.getPerPageNum())
                .queryParam("searchType", ((PageSearchCriteria) criteria).getSearchType())
                .queryParam("keyword", ((PageSearchCriteria) criteria).getKeyword())
                .build();

        return uri.toUriString();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("totalCount", totalCount)
                .append("startPage", startPage)
                .append("endPage", endPage)
                .append("prev", prev)
                .append("next", next)
                .append("displayPageNum", displayPageNum)
                .toString();
    }

    private void calcData() {
        // 현재 페이지를 기준으로 마지막 페이지 번호 계산
        // (현재 페이지 번호 / 페이지 번호의 수) * 보여지는 페이지 번호 수
        endPage = (long) (Math.ceil(criteria.getPage() / (double) displayPageNum)
                * displayPageNum);

        // (마지막 페이지 번호 - 보여지는 페이지 번호 수) + 1
        startPage = (endPage - displayPageNum) + 1;

        // 데이터 수를 기준으로 마지막 페이지 번호 계산
        long tempEndPage = (long) (Math.ceil(totalCount / (double) displayPageNum));

        // 보여져야 할 번호 수에 필요한 데이터보다 총 데이터 수가 모자르다면 마지막 페이지 번호를 조정
        if (endPage > tempEndPage) {
            endPage = tempEndPage;
        }

        // 1 = false, 그 외는 true
        prev = startPage != 1;

        // 뒤에 데이터가 더 남아 있다면 true, 적다면 false
        next = endPage * displayPageNum < totalCount;
    }
}
