package net.gentledot.springcodeproject.model.member;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.LocalDate;

public class Member {
    private String userid;
    private String userpw;
    private String username;
    private String email;
    private LocalDate regDate;
    private LocalDate updateDate;

    public String getUserid() {
        return userid;
    }

    public String getUserpw() {
        return userpw;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public LocalDate getRegDate() {
        return regDate;
    }

    public LocalDate getUpdateDate() {
        return updateDate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("userid", userid)
                .append("userpw", userpw)
                .append("username", username)
                .append("email", email)
                .append("regDate", regDate)
                .append("updateDate", updateDate)
                .toString();
    }
}
