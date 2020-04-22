package net.gentledot.springcodeproject.controllers;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = {SampleController.class})
class SampleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("doA 주소 접속 시 정상적으로 실행되는지 확인")
    void doATest() throws Exception {
        ResultActions perform = mockMvc.perform(get("/web/doA"));
        perform.andDo(print());
    }

    @Test
    @DisplayName("doE 주소 접속 시 정상적으로 실행되는지 확인")
    void doETest() throws Exception {
        ResultActions perform = mockMvc.perform(get("/web/doE"));
        perform.andDo(print());
    }

}