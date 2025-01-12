package yyytir777.persist.domain.log.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import yyytir777.persist.domain.category.entity.Category;
import yyytir777.persist.domain.log.entity.Log;
import yyytir777.persist.domain.member.entity.Member;

@DataJpaTest
class LogCustomRepositoryTest {

    @Autowired LogRepository logRepository;


    @BeforeEach
    void initData() {
        Member member = new Member();
        Category category = new Category(member);
        Log log = new Log(category);

        logRepository.save(log);
    }

    @Test
    void findByMemberId() {
    }

    @Test
    void findLogAndMemberById() {
    }

    @Test
    void increaseViewCountByLogId() {
    }

    @Test
    void findAllWithMember() {
    }

    @Test
    void findAllByCategoryId() {
    }
}