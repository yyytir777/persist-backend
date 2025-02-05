package yyytir777.persist.domain.log.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import yyytir777.persist.domain.category.CategoryTestConverter;
import yyytir777.persist.domain.category.entity.Category;
import yyytir777.persist.domain.category.repository.CategoryRepository;
import yyytir777.persist.domain.log.LogTestConverter;
import yyytir777.persist.domain.log.entity.Log;
import yyytir777.persist.domain.member.MemberTestConverter;
import yyytir777.persist.domain.member.entity.Member;
import yyytir777.persist.domain.member.repository.MemberRepository;
import yyytir777.persist.global.config.JpaAuditingConfig;
import yyytir777.persist.global.config.QueryDslConfig;

import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;

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
        Member saveMember = memberRepository.save(MemberTestConverter.createMemberInTest(1L));
        Category saveCategory = categoryRepository.save(CategoryTestConverter.createCategoryInTest(1L, saveMember));
        logRepository.save(LogTestConverter.createLogInTest(1L, saveCategory));
        logRepository.save(LogTestConverter.createLogInTest(2L, saveCategory));

        List<Log> result = logRepository.findByMemberId(saveMember.getId());

        assertThat(result.size()).isEqualTo(2L);

        // category 정보
        assertThat(result.get(0).getCategory().getId()).isEqualTo(1L);
        assertThat(result.get(1).getCategory().getId()).isEqualTo(1L);

        // member 정보
        assertThat(result.get(0).getCategory().getMember().getId()).isEqualTo(1L);
        assertThat(result.get(1).getCategory().getMember().getId()).isEqualTo(1L);
    }

    @Test
    @Order(3)
    @DisplayName("memberId로 사용자의 로그 찾기 (fetch join)")
    void findLogAndMemberById() {
        Member saveMember = memberRepository.save(MemberTestConverter.createMemberInTest(2L));
        Category saveCategory = categoryRepository.save(CategoryTestConverter.createCategoryInTest(2L, saveMember));
        Log log1 = logRepository.save(LogTestConverter.createLogInTest(3L, saveCategory));
        Log log2 = logRepository.save(LogTestConverter.createLogInTest(4L, saveCategory));

        assertThat(log1.getId()).isEqualTo(3L);
        assertThat(log2.getId()).isEqualTo(4L);

        // category, member 정보까지 fetch되었는지
        assertThat(log1.getCategory().getId()).isEqualTo(2L);
        assertThat(log2.getCategory().getId()).isEqualTo(2L);

        assertThat(log1.getCategory().getMember().getId()).isEqualTo(2L);
        assertThat(log2.getCategory().getMember().getId()).isEqualTo(2L);
    }

    @Test
    @Order(4)
    @DisplayName("조회수 1증가")
    void increaseViewCountByLogId() {
        Member saveMember = memberRepository.save(MemberTestConverter.createMemberInTest(3L));
        Category saveCategory = categoryRepository.save(CategoryTestConverter.createCategoryInTest(3L, saveMember));
        Log saveLog = logRepository.save(LogTestConverter.createLogInTest(5L, saveCategory));

        assertThat(saveLog.getViewCount()).isEqualTo(0L);

        logRepository.increaseViewCountByLogId(saveLog.getId());

        em.flush();
        em.clear();

        assertThat(logRepository.findById(saveLog.getId()).get().getViewCount()).isEqualTo(1L);
    }

    @Test
    @Order(5)
    @DisplayName("모든 log fetch (member까지 fetch)")
    void findAllWithMember() {
        Member saveMember = memberRepository.save(MemberTestConverter.createMemberInTest(4L));
        Category saveCategory = categoryRepository.save(CategoryTestConverter.createCategoryInTest(4L, saveMember));
        logRepository.save(LogTestConverter.createLogInTest(6L, saveCategory));
        logRepository.save(LogTestConverter.createLogInTest(7L, saveCategory));

        em.flush();
        em.clear();

        List<Log> logList = logRepository.findAll();
        assertThat(logList.size()).isEqualTo(2L);
        assertThat(logList.get(0).getCategory().getMember().getName()).isNotNull();
    }

    @Test
    @Order(6)
    @DisplayName("categoryId로 모든 log fetch")
    void findAllByCategoryId() {
        Member saveMember = memberRepository.save(MemberTestConverter.createMemberInTest(5L));
        Category saveCategory = categoryRepository.save(CategoryTestConverter.createCategoryInTest(5L, saveMember));
        logRepository.save(LogTestConverter.createLogInTest(8L, saveCategory));
        logRepository.save(LogTestConverter.createLogInTest(9L, saveCategory));

        em.flush();
        em.clear();

        List<Log> logList = logRepository.findAllByCategoryId(saveCategory.getId());
        assertThat(logList.size()).isEqualTo(2L);
    }
}