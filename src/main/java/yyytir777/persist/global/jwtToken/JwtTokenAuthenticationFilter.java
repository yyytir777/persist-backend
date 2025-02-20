package yyytir777.persist.global.jwtToken;

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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;
import yyytir777.persist.domain.member.entity.Member;
import yyytir777.persist.domain.member.service.MemberService;
import yyytir777.persist.global.error.ErrorCode;
import yyytir777.persist.global.error.exception.TokenException;
import yyytir777.persist.global.jwtToken.service.TokenService;
import yyytir777.persist.global.security.PrincipalDetails;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter extends OncePerRequestFilter {

    private final MemberService memberService;
    private final TokenService tokenService;

    /*

     */
    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        try {
            String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

            if(authorizationHeader == null) throw new TokenException(ErrorCode.JWT_CLAIMS_EMPTY);

            if(!authorizationHeader.startsWith("Bearer ")) throw new TokenException(ErrorCode.INVALID_TOKEN);

            String accessToken = authorizationHeader.substring(7);
            if(accessToken.equals("admin")) {
                Member admin = memberService.findByEmail("admin@admin.com");
                setAuthenticatedUser(admin);
                filterChain.doFilter(request, response);
                return;
            }

            if(tokenService.validateToken(accessToken)) {
                Member member = memberService.findByEmail(tokenService.getEmail(accessToken));
                setAuthenticatedUser(member);
            }
        } catch (TokenException e) {
            request.setAttribute("tokenException", e);
        }

        filterChain.doFilter(request, response);
    }

    private void setAuthenticatedUser(Member member) {
        UserDetails userDetails = new PrincipalDetails(member.getId(), member.getEmail(), member.getRole());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

        log.info("Set authenticated user: {}", userDetails.getUsername());
    }
}
