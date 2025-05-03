package org.example.ATM_example.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.example.ATM_example.annotation.LogOperation;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
@Slf4j
public class LoggingAspect {

    @Around("execution(* org.example.ATM_example.service.*.*(..)) && !@annotation(org.example.ATM_example.annotation.LogOperation)")
    public Object logServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();
        Object[] args = joinPoint.getArgs();
        log.debug("[Service] {}.{}() - Args: {}", className, methodName, args);
        long startTime = System.currentTimeMillis();
        try {
            Object result = joinPoint.proceed();
            log.info("[Service] {}.{}() - Executed in {} ms. Result: {}",
                    className,
                    methodName,
                    System.currentTimeMillis() - startTime,
                    result);
            return result;
        } catch (Exception e) {
            log.error("[Service] {}.{}() - Failed in {} ms. Error: {}",
                    className,
                    methodName,
                    System.currentTimeMillis() - startTime,
                    e.getMessage());
            throw e;
        }
    }

    @Around("@annotation(org.example.ATM_example.annotation.LogOperation)")
    public Object logAnnotatedMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        LogOperation annotation = method.getAnnotation(LogOperation.class);

        String operationName = annotation.value();
        Object[] args = joinPoint.getArgs();

        log.info("[Operation] {} started. Params: {}", operationName, args);
        long start = System.currentTimeMillis();

        try {
            Object result = joinPoint.proceed();
            log.info("[Operation] {} completed in {} ms. Result: {}",
                    operationName,
                    System.currentTimeMillis() - start,
                    result);
            return result;
        } catch (Exception e) {
            log.error("[Operation] {} failed: {}", operationName, e.getMessage());
            throw e;
        }
    }

}
