package yyytir777.persist.domain.category.repository;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import yyytir777.persist.domain.category.CategoryTestConverter;
import yyytir777.persist.domain.category.entity.Category;
import yyytir777.persist.domain.member.MemberTestConverter;
import yyytir777.persist.domain.member.entity.Member;
import yyytir777.persist.domain.member.repository.MemberRepository;
import yyytir777.persist.global.config.JpaAuditingConfig;
import yyytir777.persist.global.config.QueryDslConfig;

import java.util.List;

@DataJpaTest
@Import({QueryDslConfig.class, JpaAuditingConfig.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class CategoryCustomRepositoryTest {

    @Autowired CategoryRepository categoryRepository;
    @Autowired MemberRepository memberRepository;

    @AfterEach
    void tearDown() {
        categoryRepository.deleteAll();
        memberRepository.deleteAll();
    }

    @Test
    @Order(1)
    @DisplayName("memberId로 category fetch")
    void findAllByMemberId() {
        Member saveMember = memberRepository.save(MemberTestConverter.createMemberInTest(1L));
        categoryRepository.save(CategoryTestConverter.createCategoryInTest(1L, saveMember, "category1"));
        categoryRepository.save(CategoryTestConverter.createCategoryInTest(2L, saveMember, "category2"));

        List<Category> categories = categoryRepository.findAllByMemberId(1L);
        Assertions.assertThat(categories.size()).isEqualTo(2);
        Assertions.assertThat(categories.get(0).getMember()).isEqualTo(saveMember);
    }

    @Test
    void findByName() {
        Member saveMember = memberRepository.save(MemberTestConverter.createMemberInTest(2L));
        categoryRepository.save(CategoryTestConverter.createCategoryInTest(3L, saveMember, "category3"));
        categoryRepository.save(CategoryTestConverter.createCategoryInTest(4L, saveMember, "category4"));

         Category category1 = categoryRepository.findByName("category3").orElseThrow();
         Category category2 = categoryRepository.findByName("category4").orElseThrow();
         Assertions.assertThat(category1.getName()).isEqualTo("category3");
         Assertions.assertThat(category2.getName()).isEqualTo("category4");
    }
}