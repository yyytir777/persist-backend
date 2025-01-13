package yyytir777.persist.domain.log.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.jdbc.core.JdbcTemplate;
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

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

@DataJpaTest
@ActiveProfiles("test")
@Import({QueryDslConfig.class, JpaAuditingConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class) // 순서를 Order로 지정
class LogCustomRepositoryTest {

    @PersistenceContext EntityManager em;
    @Autowired LogRepository logRepository;
    @Autowired MemberRepository memberRepository;
    @Autowired CategoryRepository categoryRepository;

    @Autowired JdbcTemplate jdbcTemplate;

    @AfterEach
    void setUp() {
        logRepository.deleteAll();
        categoryRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("check db")
    void checkDatabaseUsed() throws SQLException {
        String url = Objects.requireNonNull(jdbcTemplate.getDataSource()).getConnection().getMetaData().getURL();
        System.out.println("db = " + url);
    }

    @Test
    @Order(2)
    @DisplayName("memberId로 사용자의 로그 찾기")
    void findByMemberId() {
        Member saveMember = memberRepository.save(MemberTestConvertor.createMemberInTest(1L));
        Category saveCategory = categoryRepository.save(CategoryTestConvertor.createCategoryInTest(1L, saveMember));
        logRepository.save(LogTestConvertor.createLogInTest(1L, saveCategory));
        logRepository.save(LogTestConvertor.createLogInTest(2L, saveCategory));

        List<Log> result = logRepository.findByMemberId(saveMember.getId());

        Assertions.assertThat(result.size()).isEqualTo(2L);

        // category 정보
        Assertions.assertThat(result.get(0).getCategory().getId()).isEqualTo(1L);
        Assertions.assertThat(result.get(1).getCategory().getId()).isEqualTo(1L);

        // member 정보
        Assertions.assertThat(result.get(0).getCategory().getMember().getId()).isEqualTo(1L);
        Assertions.assertThat(result.get(1).getCategory().getMember().getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    @DisplayName("memberId로 사용자의 로그 찾기 (fetch join)")
    void findLogAndMemberById() {
        Member saveMember = memberRepository.save(MemberTestConvertor.createMemberInTest(2L));
        Category saveCategory = categoryRepository.save(CategoryTestConvertor.createCategoryInTest(2L, saveMember));
        Log log1 = logRepository.save(LogTestConvertor.createLogInTest(3L, saveCategory));
        Log log2 = logRepository.save(LogTestConvertor.createLogInTest(4L, saveCategory));

        Assertions.assertThat(log1.getId()).isEqualTo(3L);
        Assertions.assertThat(log2.getId()).isEqualTo(4L);

        // category, member 정보까지 fetch되었는지
        Assertions.assertThat(log1.getCategory().getId()).isEqualTo(2L);
        Assertions.assertThat(log2.getCategory().getId()).isEqualTo(2L);

        Assertions.assertThat(log1.getCategory().getMember().getId()).isEqualTo(2L);
        Assertions.assertThat(log2.getCategory().getMember().getId()).isEqualTo(2L);
    }
}