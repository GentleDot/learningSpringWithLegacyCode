package net.gentledot.springcodeproject.model.reply;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

import static com.google.common.base.Preconditions.checkArgument;

public class Reply {
    private long rno;
    private long bno;
    private String replytext;
    private String replyer;
    private LocalDateTime regdate;
    private LocalDateTime updatedate;

    protected Reply() {
    }

    public Reply(Long rno, Long bno, String replytext, String replyer, LocalDateTime regdate, LocalDateTime updatedate) {
        checkArgument(StringUtils.isNotBlank(replytext), "댓글내용은 빈 값이 될 수 없습니다.");
        checkArgument(replytext.getBytes(StandardCharsets.UTF_8).length <= 1000, "댓글내용은 1000bytes 내외로 작성 가능합니다.");
        checkArgument(StringUtils.isNotEmpty(replyer), "댓글작성자는 필수 입력 값입니다.");
        checkArgument(replyer.getBytes(StandardCharsets.UTF_8).length <= 50, "댓글작성자는 50bytes 내외로 작성 가능합니다.");

        this.rno = ObjectUtils.defaultIfNull(rno, 0L);
        this.bno = bno;
        this.replytext = replytext;
        this.replyer = replyer;
        this.regdate = ObjectUtils.defaultIfNull(regdate, LocalDateTime.now());
        this.updatedate = updatedate;
    }

    public long getRno() {
        return rno;
    }

    public long getBno() {
        return bno;
    }

    public String getReplytext() {
        return replytext;
    }

    public String getReplyer() {
        return replyer;
    }

    @JsonSerialize(using = ToStringSerializer.class)
    public LocalDateTime getRegdate() {
        return regdate;
    }

    @JsonSerialize(using = ToStringSerializer.class)
    public LocalDateTime getUpdatedate() {
        return updatedate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        Reply reply = (Reply) o;

        return new EqualsBuilder()
                .append(rno, reply.rno)
                .append(bno, reply.bno)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(rno)
                .append(bno)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("rno", rno)
                .append("bno", bno)
                .append("replytext", replytext)
                .append("replyer", replyer)
                .append("regdate", regdate)
                .append("updatedate", updatedate)
                .toString();
    }


    public static final class Builder {
        private long rno;
        private long bno;
        private String replytext;
        private String replyer;
        private LocalDateTime regdate;
        private LocalDateTime updatedate;

        public Builder(Long bno) {
            this.bno = bno;
        }

        public Builder(Reply reply) {
            this.rno = reply.rno;
            this.bno = reply.bno;
            this.replytext = reply.replytext;
            this.replyer = reply.replyer;
            this.regdate = reply.regdate;
            this.updatedate = reply.updatedate;
        }

        public Builder replytext(String replytext) {
            this.replytext = replytext;
            return this;
        }

        public Builder replyer(String replyer) {
            if (StringUtils.isBlank(this.replyer)) {
                this.replyer = replyer;
            }
            return this;
        }

        public Reply build() {
            return new Reply(rno, bno, replytext, replyer, regdate, updatedate);
        }
    }
}
