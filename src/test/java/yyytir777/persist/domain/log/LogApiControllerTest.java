package yyytir777.persist.domain.log;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import yyytir777.persist.domain.common.Role;
import yyytir777.persist.domain.common.Type;
import yyytir777.persist.domain.log.controller.LogApiController;
import yyytir777.persist.domain.member.entity.Member;

import java.util.UUID;

@WebMvcTest(LogApiController.class)
class LogApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void getLog() {
        String memberId = UUID.randomUUID().toString();
        Member.builder().Id(memberId).name("name1").role(Role.USER).type(Type.FORM).build();

        String logId = UUID.randomUUID().toString();
    }

    @Test
    void getLogList() {
    }

    @Test
    void saveLog() {
    }
}