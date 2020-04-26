package net.gentledot.springcodeproject.controllers.board;

import net.gentledot.springcodeproject.model.PageCriteria;
import net.gentledot.springcodeproject.model.board.Board;
import net.gentledot.springcodeproject.services.board.BoardService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BoardController.class)
class BoardControllerTest {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BoardService boardService;

    @Test
    @DisplayName("게시물 등록 화면 요청")
    void boardRegisterViewTest() throws Exception {
        ResultActions perform = mockMvc.perform(get("/board/register"));
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("board/register"));
    }

    @Test
    @DisplayName("게시물 등록 요청")
    void registBoardTest() throws Exception {
        // given
        Board board = new Board(2L, "testPost", "testcontent", "testUser", null, 0L);

        // when
        ResultActions perform = mockMvc.perform(post("/board/register")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("bno", "2")
                .param("title", "testPost2")
                .param("content", "testcontent2")
                .param("writer", "testUser02"));

        // then
        perform.andDo(print())
                .andExpect(status().isFound())  // status 302
                .andExpect(view().name("redirect:/board/listAll")) // redirect view
                .andExpect(flash().attribute("result", "SUCCESS")); // FlashAttribute
    }

    @Test
    @DisplayName("게시물 전체 목록 조회 및 화면 요청")
    void listAllBoardTest() throws Exception {
        ResultActions perform = mockMvc.perform(get("/board/listAll"));
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("board/listAll"));
    }

    @Test
    @DisplayName("게시물 조회 및 화면 요청")
    void readBoardTest() throws Exception {
        // given
        Board board = new Board(1L, "testPost", "testcontent", "testUser", null, 0L);

        // when
        when(boardService.read(1L)).thenReturn(board);
        ResultActions perform = mockMvc.perform(get("/board/read?bno=1"));

        // then
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("board/read"));
    }

    @Test
    @DisplayName("게시물 삭제 요청")
    void removeBoardTest() throws Exception {
        // given
        Board board = new Board(3L, "testPost", "testcontent", "testUser", null, 0L);

        // when
        ResultActions perform = mockMvc.perform(delete("/board/remove")
                                .param("bno", "3"));

        // then
        perform.andDo(print())
                .andExpect(status().isFound())  // status 302
                .andExpect(view().name("redirect:/board/listAll")) // redirect view
                .andExpect(flash().attribute("result", "SUCCESS")); // FlashAttribute
    }

    @Test
    @DisplayName("게시물 수정 페이지 요청")
    void boardModifierViewTest() throws Exception {
        // given
        Board board = new Board(1L, "testPost", "testcontent", "testUser", null, 0L);

        // when
        when(boardService.read(1L)).thenReturn(board);

        ResultActions perform = mockMvc.perform(get("/board/modify")
                                .param("bno", "1"));

        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("board/modify"));
    }

    @Test
    @DisplayName("게시물 수정 요청")
    void modifyBoardTest() throws Exception {
        // given
        Board board = new Board(3L, "testPost", "testcontent", "testUser", null, 0L);

        // when
        ResultActions perform = mockMvc.perform(put("/board/modify")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED_VALUE)
                .param("bno", "3")
                .param("title", "testPost_modified")
                .param("content", "testcontent_modified")
                .param("writer", "testUser_modified"));

        // then
        perform.andDo(print())
                .andExpect(status().isFound())  // status 302
                .andExpect(view().name("redirect:/board/listAll")) // redirect view
                .andExpect(flash().attribute("result", "SUCCESS")); // FlashAttribute
    }

    @Test
    @DisplayName("페이징 처리된 게시물 목록 조회 요청")
    void listAllBoardWithPaginationTest() throws Exception {
        ResultActions perform = mockMvc.perform(get("/board/listCri"));
        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("pageCriteria")) // net.gentledot.springcodeproject.model.PageCriteria@65b7f80f[page=1,perPageNum=10]
                .andExpect(view().name("board/listCri"));
    }
}