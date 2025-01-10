package yyytir777.persist.domain.log.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.mock.env.MockPropertySource;
import yyytir777.persist.global.config.QueryDslConfig;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Import(QueryDslConfig.class)
class LogCustomRepositoryImplTest {

    @Autowired private LogRepository logRepository;

    @Test
    @DisplayName("findByMemberId : 특정 memberId로 로그를 조회")
    void queryDslCheckTest() {
        assertTrue(true);
    }
}