package net.gentledot.springcodeproject.model.board;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class PageCriteria {
    private int page;
    private int perPageNum;

    public PageCriteria(Integer page, Integer perPageNum) {
        page = ObjectUtils.defaultIfNull(page, 1);
        perPageNum = ObjectUtils.defaultIfNull(perPageNum, 10);

        this.page = Math.max(page, 1);
        this.perPageNum = perPageNum < 10 || perPageNum > 100 ? 10 : perPageNum;
    }

    public int getPage() {
        return page;
    }

    public int getPerPageNum() {
        return perPageNum;
    }

    public int getPageStart() {
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
