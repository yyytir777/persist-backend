package yyytir777.persist.domain.log.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import yyytir777.persist.domain.category.CategoryTestConvertor;
import yyytir777.persist.domain.category.entity.Category;
import yyytir777.persist.domain.category.repository.CategoryRepository;
import yyytir777.persist.domain.log.LogTestConvertor;
import yyytir777.persist.domain.log.entity.Log;
import yyytir777.persist.domain.member.MemberTestConvertor;
import yyytir777.persist.domain.member.entity.Member;
import yyytir777.persist.domain.member.repository.MemberRepository;
import yyytir777.persist.global.config.JpaAuditingConfig;
import yyytir777.persist.global.config.QueryDslConfig;
import yyytir777.persist.global.error.ErrorCode;
import yyytir777.persist.global.error.exception.LogException;

import java.util.List;

@DataJpaTest
@ActiveProfiles("test")
@Import({QueryDslConfig.class, JpaAuditingConfig.class})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
class LogCustomRepositoryTest {

    @Autowired LogRepository logRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired CategoryRepository categoryRepository;

    @BeforeEach
    void initData() {
        System.err.println("=============================== setup ==============================");
        Member member = memberRepository.save(MemberTestConvertor.createMemberInTest(1L));
        Category category = categoryRepository.save(CategoryTestConvertor.createCategoryInTest(1L, member));
        logRepository.save(LogTestConvertor.createLogInTest(1L, category));
        logRepository.save(LogTestConvertor.createLogInTest(2L, category));
    }

    @Test
    @Order(1)
    @Transactional
    @DisplayName("test")
    void test() {
        Member member = memberRepository.findById(1L).get();
        Category category = categoryRepository.findById(1L).get();
        Log log1 = logRepository.findById(1L).get();
        Log log2 = logRepository.findById(2L).get();

        Assertions.assertThat(member.getId()).isEqualTo(1L);
        Assertions.assertThat(category.getId()).isEqualTo(1L);
        Assertions.assertThat(log1.getId()).isEqualTo(1L);
        Assertions.assertThat(log2.getId()).isEqualTo(2L);

        Member findMember = memberRepository.findByEmail("test@test.com").orElseThrow();
        Assertions.assertThat(findMember.getId()).isEqualTo(1L);
    }

    @Test
    @Order(2)
    @Transactional
    @DisplayName("memberId로 사용자의 로그 찾기")
    void findByMemberId() {
        List<Log> result = logRepository.findByMemberId(1L);
        Assertions.assertThat(result.size()).isEqualTo(2L);
    }

    @Test
    @Order(3)
    @Transactional
    @DisplayName("memberId로 사용자의 로그 찾기 (fetch join)")
    void findLogAndMemberById() {
        Log log = logRepository.findLogAndMemberById(1L)
                .orElseThrow(() -> new LogException(ErrorCode.LOG_NOT_EXIST));

        System.out.println("log = " + log);
        Assertions.assertThat(log.getId()).isEqualTo(1L);
    }
}