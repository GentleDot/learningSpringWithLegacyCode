package net.gentledot.springcodeproject.repository.board;

import net.gentledot.springcodeproject.model.PageCriteria;
import net.gentledot.springcodeproject.model.board.Board;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    @Insert(value = "INSERT INTO tbl_board (title, content, writer) " +
            "VALUES (#{title}, #{content}, #{writer})")
    Integer create(Board board);

    @Select("select bno, title, content, writer, regdate, viewcnt " +
            "from tbl_board " +
            "where bno = #{bno}")
    Optional<Board> findByBno(Long boardNo);

    @Update("update tbl_board " +
            "set " +
            "    title = #{title}, content = #{content} " +
            "where bno = #{bno}")
    Integer update(Board board);

    @Delete("delete from tbl_board " +
            "where bno = #{bno}")
    Integer delete(Long boardNo);

    @Select("select bno, title, content, writer, regdate, viewcnt " +
            "from tbl_board " +
            "order by bno desc, regdate desc")
    List<Board> findAll();

    @Select("SELECT bno, title, content, writer, regdate, viewcnt " +
            "FROM tbl_board WHERE bno > 0 ORDER BY bno DESC, regdate DESC LIMIT #{pageStart}, #{perPageNum};")
    List<Board> findAllWithPagination(PageCriteria criteria);

}
