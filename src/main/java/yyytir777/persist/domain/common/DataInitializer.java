package yyytir777.persist.domain.common;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import yyytir777.persist.domain.member.Member;
import yyytir777.persist.domain.member.MemberRepository;

@Component
@RequiredArgsConstructor
public class DataInitializer {

    private final MemberRepository memberRepository;

    @PostConstruct
    public void dataInit() {
        Member member = Member.builder()
                .name("demo")
                .role(Role.ADMIN)
                .type(Type.FORM)
                .build();

        memberRepository.save(member);
    }
}
