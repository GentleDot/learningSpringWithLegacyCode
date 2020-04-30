package net.gentledot.springcodeproject.repository.board;

import net.gentledot.springcodeproject.model.board.PageSearchCriteria;
import org.apache.ibatis.jdbc.SQL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BoardSqlProvider {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public String listSearchWithStringSQL(PageSearchCriteria criteria) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("select bno, title, content, writer, regdate, viewcnt " +
                "from tbl_board " +
                "where bno > 0 ");

        String searchType = criteria.getSearchType();
        String keyword = criteria.getKeyword();
        if (searchType != null) {
            switch (searchType) {
                case "t":
                    stringBuffer.append("and title like concat('%', ")
                            .append(keyword)
                            .append(", '%')");
                    break;
                case "c":
                    stringBuffer.append("and content like concat('%', ")
                            .append(keyword)
                            .append(", '%')");
                    break;
                case "w":
                    stringBuffer.append("and writer like concat('%', ")
                            .append(keyword)
                            .append(", '%')");
                    break;
                case "tc":
                    stringBuffer.append("and (title like concat('%', ")
                            .append(keyword)
                            .append(", '%') ")
                            .append("or content like concat('%', ")
                            .append(keyword)
                            .append(", '%')) ");
                    break;
                case "cw":
                    stringBuffer.append("and (content like concat('%', ")
                            .append(keyword)
                            .append(", '%') ")
                            .append("or writer like concat('%', ")
                            .append(keyword)
                            .append(", '%')) ");
                    break;
                case "tcw":
                    stringBuffer.append("and (title like concat('%', ")
                            .append(keyword)
                            .append(", '%') ")
                            .append("or content like concat('%', ")
                            .append(keyword)
                            .append(", '%') ")
                            .append("or writer like concat('%', ")
                            .append(keyword)
                            .append(", '%')) ");
                    break;
            }
        }

        stringBuffer.append("order by bno desc ")
                .append("limit ")
                .append(criteria.getPageStart())
                .append(", ")
                .append(criteria.getPerPageNum());

        return stringBuffer.toString();
    }

    public String listSearch(PageSearchCriteria criteria) {
        SQL boardListSQL = new SQL()
                .SELECT("bno, title, content, writer, regdate, viewcnt")
                .FROM("tbl_board")
                .WHERE("bno > 0");

        setSearchType(criteria, boardListSQL);

        String query = boardListSQL.ORDER_BY("bno desc")
                .LIMIT("#{pageStart}, #{perPageNum}")
                .toString();
        log.debug("생성된 쿼리 : {}", query);
        return query;
    }

    public String listSearchCount(PageSearchCriteria criteria){
        SQL boardListCountSQL = new SQL()
                .SELECT("count(bno)")
                .FROM("tbl_board")
                .WHERE("bno > 0");

        setSearchType(criteria, boardListCountSQL);

        String query = boardListCountSQL.toString();
        log.debug("생성된 쿼리 : {}", query);

        return query;
    }

    private void setSearchType(PageSearchCriteria criteria, SQL sql) {
        String searchType = criteria.getSearchType();
        String keyword = criteria.getKeyword();
        if (keyword != null) {
            switch (searchType) {
                case "t":
                    sql.AND()
                            .WHERE("title like concat('%', #{keyword}, '%')");
                    break;
                case "c":
                    sql.AND()
                            .WHERE("content like concat('%', #{keyword}, '%')");
                    break;
                case "w":
                    sql.AND()
                            .WHERE("writer like concat('%', #{keyword}, '%')");
                    break;
                case "tc":
                    sql.AND()
                            .WHERE("title like concat('%', #{keyword}, '%') " +
                                    "OR content like concat('%', #{keyword}, '%')");
                    break;
                case "cw":
                    sql.AND()
                            .WHERE("content like concat('%', #{keyword}, '%') " +
                                    "OR writer like concat('%', #{keyword}, '%')");
                    break;
                case "tcw":
                    sql.AND()
                            .WHERE("title like concat('%', #{keyword}, '%') " +
                                    "OR content like concat('%', #{keyword}, '%') " +
                                    "OR writer like concat('%', #{keyword}, '%') ");
                    break;
            }
        }
    }

}
