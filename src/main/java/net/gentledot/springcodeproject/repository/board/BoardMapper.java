package net.gentledot.springcodeproject.repository.board;

import net.gentledot.springcodeproject.model.board.PageCriteria;
import net.gentledot.springcodeproject.model.board.Board;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface BoardMapper {
    @Insert(value = "INSERT INTO tbl_board (title, content, writer) " +
            "VALUES (#{title}, #{content}, #{writer})")
    Integer create(Board board);

    @Select("SELECT bno, title, content, writer, regdate, viewcnt " +
            "FROM tbl_board " +
            "WHERE bno = #{bno}")
    Optional<Board> findByBno(Long boardNo);

    @Update("UPDATE tbl_board " +
            "SET " +
            "    title = #{title}, content = #{content} " +
            "WHERE bno = #{bno}")
    Integer update(Board board);

    @Delete("DELETE FROM tbl_board " +
            "WHERE bno = #{bno}")
    Integer delete(Long boardNo);

    @Select("SELECT bno, title, content, writer, regdate, viewcnt " +
            "FROM tbl_board " +
            "ORDER BY bno DESC, regdate DESC LIMIT 300")
    List<Board> findAll();

    @Select("SELECT bno, title, content, writer, regdate, viewcnt " +
            "FROM tbl_board WHERE bno > 0 ORDER BY bno DESC, regdate DESC LIMIT #{pageStart}, #{perPageNum}")
    List<Board> findAllWithPagination(PageCriteria criteria);

    @Select("select count(1) from tbl_board")
    Long countPaging();

}
