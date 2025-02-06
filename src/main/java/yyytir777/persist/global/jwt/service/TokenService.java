package yyytir777.persist.global.jwt.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import yyytir777.persist.global.error.ErrorCode;
import yyytir777.persist.global.error.exception.TokenException;
import yyytir777.persist.global.jwt.RefreshToken;
import yyytir777.persist.global.jwt.repository.TokenRepository;
import yyytir777.persist.domain.common.Role;
import yyytir777.persist.domain.member.dto.MemberInfoDto;
import yyytir777.persist.global.jwt.JwtUtil;
import yyytir777.persist.global.jwt.dto.JwtInfoDto;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;

    @Value("${jwt.access_expiration_time}")
    Long accessTokenExpireTime;

    @Value("${jwt.refresh_expiration_time}")
    Long refreshTokenExpireTime;

    //
    public JwtInfoDto createAccessTokenByRefreshToken(String refreshToken) {
        jwtUtil.validateToken(refreshToken);

        Long memberId = jwtUtil.getMemberId(refreshToken);
        validateWithInnoDBToken(refreshToken, memberId);

        String email = jwtUtil.getEmail(refreshToken);
        Date accessTokenExpireTime = jwtUtil.createExpireTime(this.accessTokenExpireTime);
        Date refreshTokenExpireTime = jwtUtil.createExpireTime(this.refreshTokenExpireTime);

        String accessToken = jwtUtil.createAccessToken(MemberInfoDto.of(memberId, email, Role.USER), accessTokenExpireTime);
        String reissueRefreshToken = jwtUtil.createRefreshToken(MemberInfoDto.of(memberId, email, Role.USER), refreshTokenExpireTime);

        return JwtInfoDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .refreshToken(reissueRefreshToken)
                .refreshTokenExpireTime(refreshTokenExpireTime)
                .build();
    }

    private void validateWithInnoDBToken(String refreshToken, Long memberId) {
        RefreshToken refreshTokenEntity = tokenRepository.findById(memberId).
                orElseThrow(() -> new TokenException(ErrorCode.INVALID_TOKEN));

        // 기존 refreshToken과 일치하지 않으면 해당 토큰 삭제 후 재 로그인 처리
        if(!refreshTokenEntity.getRefreshToken().equals(refreshToken)) {
            tokenRepository.delete(refreshTokenEntity);
            throw new TokenException(ErrorCode.NEED_TO_RE_LOGIN);
        }
    }

    public Long getMemberIdByAccessToken(String accessToken) {
        return jwtUtil.getMemberId(accessToken);
    }

    public boolean validateToken(String token) {
        return jwtUtil.validateToken(token);
    }

    public String getEmail(String token) {
        return jwtUtil.getEmail(token);
    }
}