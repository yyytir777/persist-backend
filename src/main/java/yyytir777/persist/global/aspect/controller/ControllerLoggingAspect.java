package yyytir777.persist.global.aspect.controller;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;


@Slf4j
@Aspect
@Component
public class ControllerLoggingAspect {

    @Pointcut("@within(org.springframework.web.bind.annotation.RestController)")
    private void controllerMethods() {}

    @Before("controllerMethods()")
    public void beforeParamsLog(JoinPoint joinPoint) {
        Method method = getMethod(joinPoint);
        String className = joinPoint.getTarget().getClass().getSimpleName();
        log.info("[{}] in {} was called", method.getName(), className);
    }

    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }
}