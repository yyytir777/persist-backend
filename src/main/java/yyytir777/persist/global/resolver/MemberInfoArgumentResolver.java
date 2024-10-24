package yyytir777.persist.global.resolver;

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
import yyytir777.persist.global.jwt.JwtUtil;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberInfoArgumentResolver implements HandlerMethodArgumentResolver {

    private final JwtUtil jwtUtil;

    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAccessTokenAnnotation = parameter.hasParameterAnnotation(MemberInfo.class);
        boolean hasAccessTokenType = String.class.isAssignableFrom(parameter.getParameterType());
        return hasAccessTokenType && hasAccessTokenAnnotation;
    }

    public Object resolveArgument(@NonNull MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) {
        HttpServletRequest request = (HttpServletRequest) webRequest.getNativeRequest();
        String authorizationHeader = request.getHeader("Authorization");
        String accessToken = authorizationHeader.split(" ")[1];

        return jwtUtil.getMemberId(accessToken);
    }
}
