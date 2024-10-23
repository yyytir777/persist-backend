package yyytir777.persist.domain.log;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import yyytir777.persist.domain.common.Role;
import yyytir777.persist.domain.common.Type;
import yyytir777.persist.domain.log.dto.LogSaveRequestDto;
import yyytir777.persist.domain.log.service.LogServiceImpl;
import yyytir777.persist.domain.member.Member;
import yyytir777.persist.domain.member.MemberRepository;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class LogServiceImplTest {

    //TODO 테스트코드 작성하기

    @Mock
    MemberRepository memberRepository;

    @InjectMocks
    LogServiceImpl logService;

    @BeforeEach
    void init() {
        String memberId = UUID.randomUUID().toString();
        Member member = Member.builder()
                .Id(memberId)
                .name("demo")
                .role(Role.USER)
                .type(Type.FORM)
                .build();
        memberRepository.save(member);

        LogSaveRequestDto logSaveRequestDto1 = LogSaveRequestDto.builder().title("title1").thumbnail("thumbnail1").content("content1").build();
        LogSaveRequestDto logSaveRequestDto2 = LogSaveRequestDto.builder().title("title2").thumbnail("thumbnail2").content("content2").build();
        LogSaveRequestDto logSaveRequestDto3 = LogSaveRequestDto.builder().title("title3").thumbnail("thumbnail3").content("content3").build();

        logService.save(logSaveRequestDto1);
        logService.save(logSaveRequestDto2);
        logService.save(logSaveRequestDto3);
    }

    @Test
    void getAllLogs() {

    }
}