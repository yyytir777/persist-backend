package yyytir777.persist.global.redis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import yyytir777.persist.global.jwtToken.dto.RefreshToken;
import yyytir777.persist.global.jwtToken.repository.JwtTokenRepository;


@SpringBootTest
@TestPropertySource(locations = "classpath:local.env")
class RedisRepositoryTest {

    @Autowired
    JwtTokenRepository jwtTokenRepository;

    @Test
    void redisConnection() {
        RefreshToken refreshToken = RefreshToken
                .builder()
                .memberId(1234L)
                .refreshToken("1234")
                .build();

        jwtTokenRepository.save(refreshToken);
        RefreshToken fetchedToken = jwtTokenRepository.findById(1234L).orElseThrow();

        Assertions.assertEquals(fetchedToken.getRefreshToken(), refreshToken.getRefreshToken());
        Assertions.assertEquals(fetchedToken.getMemberId(), refreshToken.getMemberId());
    }
}
