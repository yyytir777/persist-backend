package yyytir777.persist.global.jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import yyytir777.persist.domain.member.entity.Member;
import yyytir777.persist.domain.member.service.MemberService;
import yyytir777.persist.global.error.ErrorCode;
import yyytir777.persist.global.error.exception.TokenException;

import java.io.IOException;
import java.util.Collections;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            if(authorizationHeader == null) throw new TokenException(ErrorCode.JWT_CLAIMS_EMPTY);

            if(authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);

                if(jwtUtil.validateToken(token)) {
                    String email = jwtUtil.getEmail(token);
                    Member member = memberService.findByEmail(email);

                    SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getRole().toString());
                    Authentication authentication = new UsernamePasswordAuthenticationToken(member.getId(), null, Collections.singletonList(grantedAuthority));
                    SecurityContextHolder.getContext().setAuthentication(authentication);

                }
            }
        } catch (TokenException e) {
            request.setAttribute("tokenException", e);
        }

        filterChain.doFilter(request, response);
    }
}
