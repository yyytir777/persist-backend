package yyytir777.persist.global.jwtToken;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import yyytir777.persist.global.jwtToken.dto.RefreshToken;
import yyytir777.persist.global.jwtToken.repository.JwtTokenRepository;
import yyytir777.persist.domain.member.dto.MemberInfoDto;
import yyytir777.persist.global.error.ErrorCode;
import yyytir777.persist.global.error.exception.TokenException;
import yyytir777.persist.global.jwtToken.dto.JwtInfoDto;

import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenUtil {

    private final Key key;
    private final Long accessTokenExpireTime;
    private final Long refreshTokenExpireTime;
    private final JwtTokenRepository jwtTokenRepository;

    public JwtTokenUtil(@Value("${jwt.secret}") String secret,
                        @Value("${jwt.access_expiration_time}") Long accessTokenExpireTime,
                        @Value("${jwt.refresh_expiration_time}") Long refreshTokenExpireTime,
                        JwtTokenRepository jwtTokenRepository) {

        byte[] keyBytes = Decoders.BASE64.decode(secret);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpireTime = accessTokenExpireTime;
        this.refreshTokenExpireTime = refreshTokenExpireTime;
        this.jwtTokenRepository = jwtTokenRepository;
    }

    public JwtInfoDto createToken(MemberInfoDto memberInfoDto) {
        Date accessTokenExpireTime = createExpireTime(this.accessTokenExpireTime);
        Date refreshTokenExpireTime = createExpireTime(this.refreshTokenExpireTime);

        String accessToken = createAccessToken(memberInfoDto, accessTokenExpireTime);
        String refreshToken = createRefreshToken(memberInfoDto, refreshTokenExpireTime);

        return JwtInfoDto.builder()
                .grantType("Bearer")
                .accessToken(accessToken)
                .accessTokenExpireTime(accessTokenExpireTime)
                .refreshToken(refreshToken)
                .refreshTokenExpireTime(refreshTokenExpireTime)
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

    public String createRefreshToken(MemberInfoDto memberInfoDto, Date refreshTokenExpireTime) {
        String refreshToken = Jwts.builder()
                .setHeaderParam("typ", "JWT")
                .setSubject("RefreshToken")
                .setIssuedAt(new Date())
                .setExpiration(refreshTokenExpireTime)
                .claim("memberId", memberInfoDto.getMemberId())
                .claim("email", memberInfoDto.getEmail())
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();

        RefreshToken token = RefreshToken.builder()
                .memberId(memberInfoDto.getMemberId())
                .refreshToken(refreshToken)
                .build();

        jwtTokenRepository.save(token);
        return refreshToken;
    }

    public Date createExpireTime(Long expireTime) {
        return new Date(System.currentTimeMillis() + expireTime);
    }

    public String getEmail(String token) {
        return parseClaims(token).get("email", String.class);
    }

    public Long getMemberId(String token) {
        return parseClaims(token).get("memberId", Long.class);
    }

    private Claims parseClaims(String token) {
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
            throw new TokenException(ErrorCode.INVALID_TOKEN);
        } catch (ExpiredJwtException e) {
            // 만료된 토큰의 Claims를 얻음
            Claims claims = e.getClaims();
            String subject = claims.getSubject();

            if ("AccessToken".equals(subject)) {
                throw new TokenException(ErrorCode.ACCESS_TOKEN_EXPIRED);
            } else if ("RefreshToken".equals(subject)) {
                throw new TokenException(ErrorCode.REFRESH_TOKEN_EXPIRED);
            } else {
                throw new TokenException(ErrorCode.INVALID_TOKEN);
            }
        } catch (UnsupportedJwtException e) {
            throw new TokenException(ErrorCode.UNSUPPORTED_TOKEN);
        } catch (IllegalArgumentException e) {
            throw new TokenException(ErrorCode.JWT_CLAIMS_EMPTY);
        }
    }
}
