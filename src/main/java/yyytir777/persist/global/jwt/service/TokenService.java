package yyytir777.persist.global.jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import yyytir777.persist.domain.common.Role;
import yyytir777.persist.domain.member.dto.MemberInfoDto;
import yyytir777.persist.global.jwt.JwtUtil;
import yyytir777.persist.global.jwt.dto.AccessTokenResponseDto;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtil jwtUtil;

    @Value("${jwt.access_expiration_time}")
    Long accessTokenExpireTime;

    public AccessTokenResponseDto createAccessTokenByRefreshToken(String refreshToken) {
        String memberId = jwtUtil.getMemberId(refreshToken);
        String email = jwtUtil.getEmail(refreshToken);
        Date accessTokenExpireTime = jwtUtil.createExpireTime(this.accessTokenExpireTime);
        String accessToken = jwtUtil.createAccessToken(MemberInfoDto.of(memberId, email, Role.USER), accessTokenExpireTime);

        return AccessTokenResponseDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .build();
    }
}
