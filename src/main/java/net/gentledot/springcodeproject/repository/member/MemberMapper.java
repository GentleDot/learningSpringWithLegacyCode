package net.gentledot.springcodeproject.repository.member;

import net.gentledot.springcodeproject.model.member.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.transaction.annotation.Transactional;

@Mapper
public interface MemberMapper {
    @Select("select now()")
    String getTime();

    @Transactional
    @Insert("insert into tbl_member (userid, userpw, username, email) " +
            "values (#{userid}, #{userpw}, #{username}, #{email})")
    Integer insertMember(Member member);

    @Select("select * " +
            "from tbl_member " +
            "where userid = #{userid}")
    Member findByUserId(@Param(value = "userid") String userId);

    @Select("select * " +
            "from tbl_member " +
            "where userid = #{userid} " +
            "and userpw = #{userpw} ")
    Member findByUserIdAndUserPw(@Param(value = "userid") String userId,
                      @Param(value = "userpw") String userPassword);
}
