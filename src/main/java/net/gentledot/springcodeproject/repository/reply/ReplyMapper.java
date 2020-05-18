package net.gentledot.springcodeproject.repository.reply;

import net.gentledot.springcodeproject.model.reply.Reply;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Optional;

@Mapper
public interface ReplyMapper {
    @Insert("insert into tbl_reply (bno, replytext, replyer, regdate)\n" +
            "values (#{bno}, #{replytext}, #{replyer}, now())")
    int save(Reply reply);

    @Select("SELECT rno, bno, replytext, replyer, regdate, updatedate\n" +
            "FROM tbl_reply\n" +
            "WHERE rno = #{rno}\n")
    Optional<Reply> findByRno(@Param("rno") Long replyNo);

    @Select("SELECT rno, bno, replytext, replyer, regdate, updatedate\n" +
            "FROM tbl_reply\n" +
            "WHERE bno = #{bno}\n" +
            "ORDER BY rno DESC")
    List<Reply> findAllByBno(@Param("bno") Long boardNo);

    @Update("update tbl_reply\n" +
            "set replytext = #{replytext}, updatedate = now()\n" +
            "where rno = #{rno}")
    int update(Reply reply);

    @Delete("delete from tbl_reply\n" +
            "where rno = #{rno}")
    int delete(@Param("rno") Long replyNo);
}
