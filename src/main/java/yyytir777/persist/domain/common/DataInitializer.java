package yyytir777.persist.domain.common;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import yyytir777.persist.domain.log.entity.Log;
import yyytir777.persist.domain.log.repository.LogRepository;
import yyytir777.persist.domain.member.Member;
import yyytir777.persist.domain.member.MemberRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final MemberRepository memberRepository;
    private final LogRepository logRepository;

    @PostConstruct
    public void dataInit() {
        Member member = Member.builder()
                .name("demo")
                .thumbnail("demo")
                .role(Role.ADMIN)
                .type(Type.FORM)
                .build();

        memberRepository.save(member);

        List<Log> logList = Stream.of(
                        Log.builder().title("title1").thumbnail("thumbnail1").content("content1").member(member).build(),
                        Log.builder().title("title2").thumbnail("thumbnail2").content("content2").member(member).build(),
                        Log.builder().title("title3").thumbnail("thumbnail3").content("content3").member(member).build()
                )
                .collect(Collectors.toList());

        logRepository.saveAll(logList);
    }
}
