package yyytir777.persist.global.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import yyytir777.persist.domain.member.dto.MemberInfoDto;
import yyytir777.persist.global.error.ErrorCode;
import yyytir777.persist.global.error.exception.TokenException;
import yyytir777.persist.global.jwt.dto.JwtInfoDto;

import java.security.Key;
import java.util.Date;

@Slf4j
@Component
public class JwtUtil {

    private final Key key;
    private final Long accessTokenExpireTime;
    private final Long refreshTokenExpireTime;

    public JwtUtil(@Value("${jwt.secret}") String secret,
                   @Value("${jwt.access_expiration_time}") Long accessTokenExpireTime,
                   @Value("${jwt.refresh_expiration_time}") Long refreshTokenExpireTime) {

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpireTime = accessTokenExpireTime;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
    }

    public JwtInfoDto createToken(MemberInfoDto memberInfoDto) {
        Date accessTokenExpireTime = createExpireTime(this.accessTokenExpireTime);
        Date refreshTokenExpireTime = createExpireTime(this.refreshTokenExpireTime);

        String accessToken = createAccessToken(memberInfoDto, accessTokenExpireTime);
        String refreshToken = createRefreshtoken(memberInfoDto, refreshTokenExpireTime);

        return JwtInfoDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .refreshToken(refreshToken)
                .refreshTokenExpiretime(refreshTokenExpireTime)
                .build();
    }

    public String createAccessToken(MemberInfoDto memberInfoDto, Date accessTokenExpireTime) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject("AccessToken")
                .setIssuedAt(new Date())
                .setExpiration(accessTokenExpireTime)
                .claim("memberId", memberInfoDto.getMemberId())
                .claim("email", memberInfoDto.getEmail())
                .claim("role", memberInfoDto.getRole())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    private String createRefreshtoken(MemberInfoDto memberInfoDto, Date refreshTokenExpireTime) {
        return Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject("RefreshToken")
                .setIssuedAt(new Date())
                .setExpiration(refreshTokenExpireTime)
                .claim("memberId", memberInfoDto.getMemberId())
                .claim("email", memberInfoDto.getEmail())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public Date createExpireTime(Long expireTime) {
        return new Date(System.currentTimeMillis() + expireTime);
    }

    public String getEmail(String token) {
        return parseCliams(token).get("email", String.class);
    }

    public String getMemberId(String token) {
        return parseCliams(token).get("memberId", String.class);
    }

    private Claims parseCliams(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            log.info("JWT 토큰이 유효하지 않습니다.", e);
            throw new TokenException(ErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            log.info("JWT 토큰이 만료되었습니다.", e);
            throw new TokenException(ErrorCode.ACCESS_TOKEN_EXPIRED);
        } catch (UnsupportedJwtException e) {
            log.info("지원하지 않는 JWT 토큰 입니다.", e);
            throw new TokenException(ErrorCode.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            log.info("JWT claims가 비어있습니다.", e);
            throw new TokenException(ErrorCode.JWT_CLAIMS_EMPTY);
        }
    }
}
