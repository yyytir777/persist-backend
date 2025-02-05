package yyytir777.persist.global.config;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import yyytir777.persist.global.properties.JwtProperties;
import yyytir777.persist.global.properties.RedisProperties;

@Configuration
@EnableConfigurationProperties({RedisProperties.class, JwtProperties.class})
public class PropertiesConfig {
}
