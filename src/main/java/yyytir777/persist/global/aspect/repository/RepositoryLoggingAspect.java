package yyytir777.persist.global.aspect.repository;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class RepositoryLoggingAspect {

    @Before("@within(org.springframework.stereotype.Repository)")
    public void beforeRepositoryMethods(JoinPoint joinPoint) {
        Method method = getMethod(joinPoint);
        Class<?>[] interfaces = joinPoint.getTarget().getClass().getInterfaces();
        String interfaceName = interfaces.length > 0 ? interfaces[0].getSimpleName() : "unknown Interface";

        log.info("[{}] in {} executed", method.getName(), interfaceName);
    }

    private Method getMethod(JoinPoint joinPoint) {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        return signature.getMethod();
    }
}
