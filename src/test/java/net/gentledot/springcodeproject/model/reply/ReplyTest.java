package net.gentledot.springcodeproject.model.reply;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

class ReplyTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    @DisplayName("댓글 객체 생성 테스트")
    void createReplyTest(){
        Reply reply = new Reply.Builder(1L)
                .replyer("hello")
                .replytext("world")
                .build();

        assertThat(reply, is(notNullValue()));
        log.debug("생성된 댓글 : {}", reply);
    }
}