package yyytir777.persist.global.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yyytir777.persist.domain.common.Role;
import yyytir777.persist.domain.member.entity.Member;
import yyytir777.persist.domain.member.repository.MemberRepository;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class AdminAccountInitializer {

    private final MemberRepository memberRepository;
    private final String name = "ADMIN";
    private final Role role = Role.ADMIN;

    @Bean
    public CommandLineRunner init() {
        return args -> {

            if(memberRepository.findByEmail("admin@admin.com").isEmpty()) {
                Member member = Member.builder()
                        .email("admin@admin.com")
                        .role(role)
                        .name(name)
                        .build();

                memberRepository.save(member);
            } else {
                log.info("Member already exists");
            }
        };
    }
}
