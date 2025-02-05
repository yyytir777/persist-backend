package yyytir777.persist.global.redis;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import yyytir777.persist.config.TestRedisConfig;

@DataRedisTest
@ActiveProfiles("test")
@Import(TestRedisConfig.class)
@TestPropertySource(locations = "classpath:local.env")
public class RedisConnectionTest {

    @Autowired
    private StringRedisTemplate template;

    @Test
    void testRedisConnection() {
        ValueOperations<String, String> valueOperations = template.opsForValue();

        for(int i = 0; i < 100; i++) {
            String key = "key" + i;
            String value = "value" + i;
            valueOperations.set(key, value);
        }

        String value = valueOperations.get("key1");
        Assertions.assertThat(value).isEqualTo("value1");
    }
}
