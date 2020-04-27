package net.gentledot.springcodeproject.model.board;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PageCriteria {
    private long page;
    private long perPageNum;

    public PageCriteria(Long page, Long perPageNum) {
        page = ObjectUtils.defaultIfNull(page, 1L);
        perPageNum = ObjectUtils.defaultIfNull(perPageNum, 10L);

        this.page = Math.max(page, 1);
        this.perPageNum = perPageNum < 10 || perPageNum > 100 ? 10 : perPageNum;
    }

    public long getPage() {
        return page;
    }

    public long getPerPageNum() {
        return perPageNum;
    }

    public long getPageStart() {
        return (page - 1) * perPageNum;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("page", page)
                .append("perPageNum", perPageNum)
                .toString();
    }
}
