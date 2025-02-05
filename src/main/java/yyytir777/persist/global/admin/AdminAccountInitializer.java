package yyytir777.persist.global.admin;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import yyytir777.persist.domain.category.CategoryTestConverter;
import yyytir777.persist.domain.category.entity.Category;
import yyytir777.persist.domain.category.repository.CategoryRepository;
import yyytir777.persist.domain.common.Role;
import yyytir777.persist.domain.log.LogTestConverter;
import yyytir777.persist.domain.log.entity.Log;
import yyytir777.persist.domain.log.repository.LogRepository;
import yyytir777.persist.domain.member.MemberTestConverter;
import yyytir777.persist.domain.member.entity.Member;
import yyytir777.persist.domain.member.repository.MemberRepository;

import java.util.UUID;

@Slf4j
//@Configuration
@RequiredArgsConstructor
public class AdminAccountInitializer {

    private final MemberRepository memberRepository;
    private final CategoryRepository categoryRepository;
    private final LogRepository logRepository;

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

    @Bean
    public CommandLineRunner init2() {

        return args -> {
            for(int i = 0; i < 10; i++) {
                String email = UUID.randomUUID().toString();
                Member member = MemberTestConverter.createMemberInTest(email);

                memberRepository.save(member);

                Category category = CategoryTestConverter.createCategoryInTest(member);
                categoryRepository.save(category);

                Log log = LogTestConverter.createLogInTest(category);
                logRepository.save(log);
            }
        };
    }
}
