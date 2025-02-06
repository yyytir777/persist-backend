package yyytir777.persist.global.config;


import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import yyytir777.persist.domain.common.Role;
import yyytir777.persist.domain.member.service.MemberService;
import yyytir777.persist.global.geoip.IpAuthenticationFilter;
import yyytir777.persist.global.handler.CustomAccessDeniedHandler;
import yyytir777.persist.global.handler.CustomAuthenticationEntryPoint;
import yyytir777.persist.global.jwt.JwtAuthenticationFilter;
import yyytir777.persist.global.jwt.service.TokenService;

import java.util.Optional;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@Profile({"local", "prod"})
public class SecurityConfig {

    private final TokenService tokenService;
    private final MemberService memberService;

    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

    @Autowired(required = false)
    private Optional<IpAuthenticationFilter> optionalIpAuthenticationFilter;

    private static final String[] AUTH_WHITELIST = {
            //swagger
            "/swagger-ui/*", "/swagger-resources/*", "/v3/api-docs/**",

            // social login
            "/api/v1/oauth/**",

            // social login callback
            "/oauth/**",

            // register
            "/api/v1/member/register",

            // healthCheck
            "/health/**",

            "/",

            // reissue accessToken by refreshToken
            "/api/v1/token/reissue",
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable);

        httpSecurity
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        httpSecurity
                .addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);

        optionalIpAuthenticationFilter.ifPresent(optionalIpAuthenticationFilter ->
                httpSecurity
                    .addFilterBefore(optionalIpAuthenticationFilter, JwtAuthenticationFilter.class));

        httpSecurity
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(authenticationEntryPoint)
                        .accessDeniedHandler(accessDeniedHandler));

        httpSecurity
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(AUTH_WHITELIST).permitAll()
                        .requestMatchers("/admin/**").hasAuthority(Role.ADMIN.toString())
                        .anyRequest().authenticated());

        return httpSecurity.build();
    }

    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter() {
        return new JwtAuthenticationFilter(memberService, tokenService);
    }
}