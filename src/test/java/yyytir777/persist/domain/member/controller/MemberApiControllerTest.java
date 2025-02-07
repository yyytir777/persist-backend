package yyytir777.persist.domain.member.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import yyytir777.persist.domain.common.Type;
import yyytir777.persist.domain.member.dto.MemberRegisterRequestDto;
import yyytir777.persist.domain.member.service.MemberService;

@WebMvcTest(MemberApiController.class)
@AutoConfigureMockMvc(addFilters = false)
class MemberApiControllerTest {

    @Autowired MockMvc mockMvc;
    @Autowired ObjectMapper objectMapper;
    @MockBean MemberService memberService;
    
    @Test
    @DisplayName("유저 회원가입")
    void register() throws Exception {
        MemberRegisterRequestDto requestDto =
                new MemberRegisterRequestDto("email", "name", "logName", Type.KAKAO);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/member/register")
                .content(objectMapper.writeValueAsBytes(requestDto))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        Mockito.verify(memberService, Mockito.times(1)).register(Mockito.any(MemberRegisterRequestDto.class));
    }
}