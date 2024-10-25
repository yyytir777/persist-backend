package yyytir777.persist.global;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import yyytir777.persist.domain.member.entity.Member;
import yyytir777.persist.domain.member.repository.MemberRepository;
import yyytir777.persist.global.error.ErrorCode;
import yyytir777.persist.global.error.exception.MemberException;

import java.util.Collections;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final MemberRepository memberRepository;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String email = (String) authentication.getPrincipal();

        Member member = memberRepository.findByEmail(email).orElseThrow(() ->
                new MemberException(ErrorCode.MEMBER_NOT_EXIST));

        SimpleGrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getRole().toString());
        return new UsernamePasswordAuthenticationToken(email, null, Collections.singletonList(grantedAuthority));
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return false;
    }
}
