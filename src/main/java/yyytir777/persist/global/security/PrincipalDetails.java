package yyytir777.persist.global.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import yyytir777.persist.domain.common.Role;

import java.util.Collection;
import java.util.Collections;

@AllArgsConstructor
public class PrincipalDetails implements UserDetails {

    private final Long memberId;
    @Getter
    private final String email;

    private final Role memberRole;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + memberRole.getRole()));
    }

    public String getPassword() {
        return null;
    }

    public String getUsername() {
        return memberId.toString();
    }

}
