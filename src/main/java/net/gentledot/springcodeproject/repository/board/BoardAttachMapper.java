package net.gentledot.springcodeproject.repository.board;

import net.gentledot.springcodeproject.model.board.AttachFile;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BoardAttachMapper {
    @Insert("insert into tbl_attach (uuid, bno, uploadPath, fileName, fileType)\n" +
            "values (#{uuid}, #{bno}, #{uploadPath}, #{fileName}, #{fileType})")
    Integer insert(AttachFile attachFile);

    @Delete("delete from tbl_attach where uuid = #{uuid}")
    Integer delete(String uuid);

    @Select("select uuid, bno, uploadPath, fileName, fileType\n " +
            "from tbl_attach where bno = #{bno}")
    List<AttachFile> findAllByBno(Long bno);
}
