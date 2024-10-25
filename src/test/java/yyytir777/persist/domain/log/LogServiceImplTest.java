package yyytir777.persist.domain.log;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import yyytir777.persist.domain.common.Role;
import yyytir777.persist.domain.common.Type;
import yyytir777.persist.domain.log.dto.LogCreateRequestDto;
import yyytir777.persist.domain.log.service.LogServiceImpl;
import yyytir777.persist.domain.member.entity.Member;
import yyytir777.persist.domain.member.repository.MemberRepository;

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

        LogCreateRequestDto logCreateRequestDto1 = LogCreateRequestDto.builder().title("title1").thumbnail("thumbnail1").content("content1").build();
        LogCreateRequestDto logCreateRequestDto2 = LogCreateRequestDto.builder().title("title2").thumbnail("thumbnail2").content("content2").build();
        LogCreateRequestDto logCreateRequestDto3 = LogCreateRequestDto.builder().title("title3").thumbnail("thumbnail3").content("content3").build();

    }

    @Test
    void getAllLogs() {

    }
}