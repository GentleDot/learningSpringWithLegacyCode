package net.gentledot.springcodeproject.repository.reply;

import net.gentledot.springcodeproject.model.reply.Reply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.time.LocalDateTime;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ReplyMapperTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    ReplyMapper replyMapper;

    @Test
    @DisplayName("댓글 저장 테스트")
    void saveTest() {
        Reply reply = getTestReply(1L, "hello", "world");

        // insert into tbl_reply (bno, replytext, replyer, regdate) values (1, 'world', 'hello', 2020-05-07T17:33:18.823321)
        int result = replyMapper.save(reply);

        assertThat(result, is(1));
    }

    @Test
    @DisplayName("선택 게시물의 댓글 목록 조회 테스트")
    void findAllByBno() {
        long boardNo = 1L;
        List<Reply> replyList = replyMapper.findAllByBno(boardNo);

        assertThat(replyList, is(notNullValue()));
        assertThat(replyList.get(0).getRno(), is(1L));
        assertThat(replyList.get(0).getBno(), is(1L));
        assertThat(replyList.get(0).getReplytext(), is("테스트 댓글"));
        assertThat(replyList.get(0).getReplyer(), is("테스터01"));
        assertThat(replyList.get(0).getRegdate(), is(notNullValue()));
        assertThat(replyList.get(0).getUpdatedate(), is(notNullValue()));

        log.debug("조회된 댓글 목록 : {}", replyList);
    }

    @Test
    @DisplayName("댓글 수정 테스트")
    void updateTest() {
        long replyNo = 1L;
        Reply oldReply = replyMapper.findByRno(replyNo).orElse(null);

        String beforeReplytext = oldReply.getReplytext();
        String beforeReplyer = oldReply.getReplyer();
        LocalDateTime updatedate = oldReply.getUpdatedate();

        Reply modifiedReply = new Reply.Builder(oldReply)
                .replytext("관리자에 의해 수정된 댓글입니다.")
                .replyer("tester02")
                .build();

        int result = replyMapper.update(modifiedReply);
        Reply reply = replyMapper.findByRno(replyNo).orElse(null);

        assertThat(result, is(1));
        assertThat(reply.getReplyer().equals("tester02"), is(false));
        assertThat(reply.getReplytext().equals(oldReply), is(false));
        assertThat(reply.getReplytext().equals("관리자에 의해 수정된 댓글입니다."), is(true));
        assertThat(reply.getUpdatedate(), is(notNullValue()));
    }

    @Test
    void deleteTest() {
        int result = replyMapper.delete(1L);

        assertThat(result, is(1));
    }

    private Reply getTestReply(Long bno, String text, String user) {
        return new Reply.Builder(bno)
                .replyer(text)
                .replytext(user)
                .build();
    }
}