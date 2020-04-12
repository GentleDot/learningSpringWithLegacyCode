package net.gentledot.springcodeproject.repository.member;

import net.gentledot.springcodeproject.model.member.Member;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

public interface MemberMapper {
    @Select("select now()")
    public String getTime();

    @Insert("insert into tbl_member (userid, userpw, username, email) values (#{userid}, #{userpw}, #{username}, #{email}")
    public void insertMember(Member member);
}
