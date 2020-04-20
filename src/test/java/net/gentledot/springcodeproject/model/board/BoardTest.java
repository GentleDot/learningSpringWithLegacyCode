package net.gentledot.springcodeproject.model.board;

import org.hamcrest.MatcherAssert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

class BoardTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Test
    @DisplayName("Board 객체 생성 테스트")
    void createBoardTest(){
        String title = "테스트타이틀";
        String content = "안녕하세요. 테스트 내용입니다.";
        String username = "testUser01";
        Board newBoard = new Board.Builder()
                .title(title)
                .content(content)
                .writer(username)
                .build();

        assertThat(newBoard.getBno(), is(0L));
        assertThat(newBoard.getTitle(), is(title));
        assertThat(newBoard.getContent(), is(content));
        assertThat(newBoard.getWriter(), is(username));
        assertThat(newBoard.getRegDate(), is(notNullValue()));
        assertThat(newBoard.getViewcnt(), is(0L));

        log.debug("생성된 객체 : {}", newBoard);
    }

    @Test
    @DisplayName("Board 객체 생성 후 view count 증가 테스트")
    void createBoardTestWithIncreaseViewCnt(){
        String title = "테스트타이틀";
        String content = "안녕하세요. 테스트 내용입니다.";
        String username = "testUser01";
        Board newBoard = new Board.Builder()
                .title(title)
                .content(content)
                .writer(username)
                .build();
        long beforeViewCnt = newBoard.getViewcnt();

        long viewcnt = newBoard.increaseAndGetViewcnt();

        assertThat(newBoard.getBno(), is(0L));
        assertThat(newBoard.getTitle(), is(title));
        assertThat(newBoard.getContent(), is(content));
        assertThat(newBoard.getWriter(), is(username));
        assertThat(newBoard.getRegDate(), is(notNullValue()));
        assertThat(beforeViewCnt, is(0L));
        assertThat(newBoard.getViewcnt() == beforeViewCnt, is(false));
        assertThat(newBoard.getViewcnt(), is(viewcnt));

        log.debug("생성된 객체 : {}", newBoard);
    }
}