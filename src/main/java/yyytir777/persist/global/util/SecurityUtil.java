package yyytir777.persist.global.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import yyytir777.persist.global.error.ErrorCode;
import yyytir777.persist.global.error.exception.TokenException;
import yyytir777.persist.global.security.PrincipalDetails;

@Component
public class SecurityUtil {

    public Long getCurrentMemberId() {
        PrincipalDetails principal = (PrincipalDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        try {
            return Long.valueOf(principal.getUsername());
        } catch (Exception e) {
            throw new TokenException(ErrorCode.AUTH_NOT_FOUND);
        }
    }
}
