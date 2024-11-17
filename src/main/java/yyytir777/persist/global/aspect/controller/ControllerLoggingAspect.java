package yyytir777.persist.global.aspect.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Arrays;


@Slf4j
@Aspect
@Component
public class ControllerLoggingAspect {

    @Before("@within(org.springframework.web.bind.annotation.RestController)")
    public void beforeControllerMethods(JoinPoint joinPoint) {
        Method method = getMethod(joinPoint);
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String clientIp = getclientIp();
        String mappingInfo = getClassMappingInfo(joinPoint.getTarget().getClass()) + getMethodMappingInfo(method);
        log.info("[{}] in {} ({}) was called from {}", method.getName(), className, mappingInfo, clientIp);
    }

    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }

    private String getClassMappingInfo(Class<?> targetClass) {
        String url;
        if (targetClass.isAnnotationPresent(RequestMapping.class)) {
            url = Arrays.toString(targetClass.getAnnotation(RequestMapping.class).value());
            return url.substring(1, url.length() - 1);
        }
        return "";
    }

    private String getMethodMappingInfo(Method method) {
        String mappingValue = "";

        if (method.isAnnotationPresent(GetMapping.class)) {
            mappingValue = Arrays.toString(method.getAnnotation(GetMapping.class).value());
        } else if (method.isAnnotationPresent(PostMapping.class)) {
            mappingValue = Arrays.toString(method.getAnnotation(PostMapping.class).value());
        } else if (method.isAnnotationPresent(PutMapping.class)) {
            mappingValue = Arrays.toString(method.getAnnotation(PutMapping.class).value());
        } else if (method.isAnnotationPresent(DeleteMapping.class)) {
            mappingValue = Arrays.toString(method.getAnnotation(DeleteMapping.class).value());
        } else if (method.isAnnotationPresent(RequestMapping.class)) {
            mappingValue = Arrays.toString(method.getAnnotation(RequestMapping.class).value());
        }
        return mappingValue.substring(1, mappingValue.length() - 1);
    }

    private String getclientIp() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes == null) {
            return "Unkown IP";
        }

        HttpServletRequest request = attributes.getRequest();
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) ip = request.getRemoteAddr();

        if (ip.equals("0:0:0:0:0:0:0:1")) return "localhost";
        return ip;
    }
}