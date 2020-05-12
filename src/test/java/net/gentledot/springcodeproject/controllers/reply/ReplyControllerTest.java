package net.gentledot.springcodeproject.controllers.reply;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.gentledot.springcodeproject.model.reply.Reply;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ReplyControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @DisplayName("신규 댓글 생성 요청 테스트")
    @Transactional
    void registReplyTest() throws Exception {
        Reply reply = new Reply.Builder(1L)
                .replytext("안녕하세요.")
                .replyer("tester01")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonReplyRequest = objectMapper.writeValueAsString(reply);

        ResultActions perform = mockMvc.perform(post("/replies/new")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonReplyRequest));

        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("success"));
    }

    @Test
    @DisplayName("해당하는 번호의 게시물 내 모든 댓글 목록 조회 요청 테스트")
    void getRepliesInBoardTest() throws Exception {

        ResultActions perform = mockMvc.perform(get("/replies/board/1"));

        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("application/json"));
    }

    @Test
    @DisplayName("댓글 수정 요청 테스트")
    @Transactional
    void modifyReplyTest() throws Exception {
        Reply reply = new Reply.Builder(1L)
                .replytext("reply modified.")
                .replyer("tester001")
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonReplyRequest = objectMapper.writeValueAsString(reply);

        ResultActions perform = mockMvc.perform(put("/replies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonReplyRequest));

        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("success"));
    }

    @Test
    @DisplayName("댓글 삭제 요청 테스트")
    @Transactional
    void removeReply() throws Exception {
        ResultActions perform = mockMvc.perform(delete("/replies/1"));

        perform.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType("text/plain;charset=UTF-8"))
                .andExpect(content().string("success"));
    }
}