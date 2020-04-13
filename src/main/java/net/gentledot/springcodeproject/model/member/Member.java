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

    protected Member() {
    }

    public Member(String userid, String userpw, String username, String email, LocalDate regDate, LocalDate updateDate) {
        this.userid = userid;
        this.userpw = userpw;
        this.username = username;
        this.email = email;
        this.regDate = regDate;
        this.updateDate = updateDate;
    }

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


    public static class Builder {
        private final String userid;
        private String userpw;
        private String username;
        private String email;

        public Builder(String userid) {
            this.userid = userid;
        }

        public Builder userpw(String userpw) {
            this.userpw = userpw;
            return this;
        }

        public Builder username(String username) {
            this.username = username;
            return this;
        }

        public Builder email(String email) {
            this.email = email;
            return this;
        }

        public Member build() {
            return new Member(userid, userpw, username, email, null, null);
        }
    }


}
