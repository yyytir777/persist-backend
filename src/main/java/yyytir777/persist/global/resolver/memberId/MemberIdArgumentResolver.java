package yyytir777.persist.global.resolver.memberId;

import jakarta.servlet.http.HttpServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import yyytir777.persist.global.error.ErrorCode;
import yyytir777.persist.global.error.exception.TokenException;
import yyytir777.persist.global.jwt.JwtUtil;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberIdArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtil jwtUtil;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasMemberIdAnnotation = parameter.hasParameterAnnotation(MemberId.class);
        boolean hasMemberIdDto = MemberIdDto.class.isAssignableFrom(parameter.getParameterType());
        return hasMemberIdAnnotation && hasMemberIdDto;
    }

    @Override
    public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader == null) throw new TokenException(ErrorCode.HEADER_IS_NULL);

        String accessToken = authorizationHeader.split(" ")[1];
        jwtUtil.validateToken(accessToken);
        Long memberId = jwtUtil.getMemberId(accessToken);
        return MemberIdDto.builder().memberId(memberId).build();
    }
}
