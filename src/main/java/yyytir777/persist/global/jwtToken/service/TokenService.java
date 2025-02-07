package yyytir777.persist.global.jwtToken.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import yyytir777.persist.global.error.ErrorCode;
import yyytir777.persist.global.error.exception.TokenException;
import yyytir777.persist.global.jwtToken.dto.RefreshToken;
import yyytir777.persist.global.jwtToken.repository.JwtTokenRepository;
import yyytir777.persist.domain.common.Role;
import yyytir777.persist.domain.member.dto.MemberInfoDto;
import yyytir777.persist.global.jwtToken.JwtTokenUtil;
import yyytir777.persist.global.jwtToken.dto.JwtInfoDto;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class TokenService {

    private final JwtTokenUtil jwtTokenUtil;
    private final JwtTokenRepository jwtTokenRepository;

    @Value("${jwt.access_expiration_time}")
    Long accessTokenExpireTime;

    @Value("${jwt.refresh_expiration_time}")
    Long refreshTokenExpireTime;

    //
    public JwtInfoDto createAccessTokenByRefreshToken(String refreshToken) {
        jwtTokenUtil.validateToken(refreshToken);

        Long memberId = jwtTokenUtil.getMemberId(refreshToken);
        validateWithInnoDBToken(refreshToken, memberId);

        String email = jwtTokenUtil.getEmail(refreshToken);
        Date accessTokenExpireTime = jwtTokenUtil.createExpireTime(this.accessTokenExpireTime);
        Date refreshTokenExpireTime = jwtTokenUtil.createExpireTime(this.refreshTokenExpireTime);

        String accessToken = jwtTokenUtil.createAccessToken(MemberInfoDto.of(memberId, email, Role.USER), accessTokenExpireTime);
        String reissueRefreshToken = jwtTokenUtil.createRefreshToken(MemberInfoDto.of(memberId, email, Role.USER), refreshTokenExpireTime);

        return JwtInfoDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .refreshToken(reissueRefreshToken)
                .refreshTokenExpireTime(refreshTokenExpireTime)
                .build();
    }

    private void validateWithInnoDBToken(String refreshToken, Long memberId) {
        RefreshToken refreshTokenEntity = jwtTokenRepository.findById(memberId).
                orElseThrow(() -> new TokenException(ErrorCode.INVALID_TOKEN));

        // 기존 refreshToken과 일치하지 않으면 해당 토큰 삭제 후 재 로그인 처리
        if(!refreshTokenEntity.getRefreshToken().equals(refreshToken)) {
            jwtTokenRepository.delete(refreshTokenEntity);
            throw new TokenException(ErrorCode.NEED_TO_RE_LOGIN);
        }
    }

    public Long getMemberIdByAccessToken(String accessToken) {
        return jwtTokenUtil.getMemberId(accessToken);
    }

    public boolean validateToken(String token) {
        return jwtTokenUtil.validateToken(token);
    }

    public String getEmail(String token) {
        return jwtTokenUtil.getEmail(token);
    }
}