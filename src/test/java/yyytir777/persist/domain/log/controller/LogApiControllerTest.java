package yyytir777.persist.domain.log.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import yyytir777.persist.config.TestSecurityConfig;


@WebMvcTest(LogApiController.class)
@Import(TestSecurityConfig.class)
class LogApiControllerTest {

    @Autowired MockMvc mockMvc;

    @Test
    void readAllLog() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/log/all"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());
    }
}