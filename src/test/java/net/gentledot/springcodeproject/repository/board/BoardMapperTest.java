package net.gentledot.springcodeproject.repository.board;

import net.gentledot.springcodeproject.model.board.Board;
import net.gentledot.springcodeproject.repository.member.BoardMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@MybatisTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class BoardMapperTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    BoardMapper boardMapper;

    @Test
    @DisplayName("게시물 생성(저장) 테스트")
    void createTest() {
        String writer = "testWriter";
        String content = "testContent";
        String title = "testTitle";
        Board board = new Board.Builder()
                .writer(writer)
                .content(content)
                .title(title)
                .build();
        Integer result = boardMapper.create(board);

        assertThat(result, is(1));
    }

    @Test
    @DisplayName("게시물 1번 조회 테스트")
    void findByBnoTest() {
        String title = "testTitle";
        String content = "testContent";
        String writer = "testUser";
        Board board = boardMapper.findByBno(1L);

        assertThat(board.getBno(), is(1L));
        assertThat(board.getTitle(), is(title));
        assertThat(board.getContent(), is(content));
        assertThat(board.getWriter(), is(writer));
        assertThat(board.getRegDate(), is(notNullValue()));
        assertThat(board.getViewcnt(), is(0L));

        log.debug("조회된 게시물 : {}", board);
    }

    @Test
    @DisplayName("게시물 1번 수정 테스트")
    void updateTest() {
        Board board = boardMapper.findByBno(1L);
        String title = board.getTitle();
        String content = board.getContent();
        String writer = board.getWriter();

        String updatedTitle = "수정된제목";
        String updatedContent = "수정된컨텐츠";
        String updatedUser = "testUser01";
        Board editedBoard = new Board.Builder(board)
                .title(updatedTitle)
                .content(updatedContent)
                .writer(updatedUser)
                .build();

        Integer result = boardMapper.update(editedBoard);

        Board updatedBoard = boardMapper.findByBno(1L);

        assertThat(result, is(1));
        assertThat(updatedBoard.getTitle().equals(title), is(false));
        assertThat(updatedBoard.getTitle(), is(updatedTitle));
        assertThat(updatedBoard.getContent().equals(content), is(false));
        assertThat(updatedBoard.getContent(), is(updatedContent));
        assertThat(updatedBoard.getWriter().equals(writer), is(true));
    }

    @Test
    @DisplayName("게시물 1번 삭제 테스트")
    void deleteTest() {
        Integer result = boardMapper.delete(1L);
        assertThat(result, is(1));

    }

    @Test
    @DisplayName("게시물 전체 조회 테스트")
    void findAllTest() {
        List<Board> list = boardMapper.findAll();

        assertThat(list, is(notNullValue()));
    }
}