package yyytir777.persist.global.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Slf4j
@Aspect
@Component
public class ErrorLoggingAspect {

    // Pointcut: handlerBusinessException 메서드
    @Before("execution(* yyytir777.persist.global.error.GlobalExceptionHandler.handlerBusinessException(..))")
    public void beforeBusinessException(JoinPoint joinPoint) {
        logRequestDetails("BusinessException handler");
    }

    // Pointcut: handlerException 메서드
    @Before("execution(* yyytir777.persist.global.error.GlobalExceptionHandler.handlerException(..))")
    public void beforeException(JoinPoint joinPoint) {
        logRequestDetails("Exception handler");
    }

    // 공통 로직: 요청 정보 로깅
    private void logRequestDetails(String handlerName) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String url = request.getRequestURI();
            String ip = getClientIp(request);
            log.warn("{} was called from URL: {}, IP: {}", handlerName, url, ip);
        } else {
            log.warn("{} was called, but request attributes are null", handlerName);
        }
    }

    // 클라이언트 IP 추출 메서드
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For"); // 프록시 뒤의 클라이언트 IP 확인
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr(); // 직접 연결된 클라이언트 IP
        }
        return ip;
    }
}
