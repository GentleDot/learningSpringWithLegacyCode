package net.gentledot.springcodeproject.repository.member;

import net.gentledot.springcodeproject.model.board.Board;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface BoardMapper {
    @Insert(value = "INSERT INTO tbl_board (title, content, writer) " +
            "VALUES (#{title}, #{content}, #{writer})")
    Integer create(Board board);

    @Select("select bno, title, content, writer, regdate, viewcnt " +
            "from tbl_board " +
            "where bno = #{bno}")
    Board findByBno(Long boardNo);

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

}
