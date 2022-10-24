package org.example.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {
    @Around("Pointcuts.createNewUser()")
    public String aroundSignupAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        String webPage = null;
        try{
            webPage = (String) joinPoint.proceed();
            log.info("New user signed up successfully.");
        } catch (Throwable throwable){
            log.error("User signing up error. " + throwable.getMessage());
            throw throwable;
        }
        return webPage;
    }

    @AfterThrowing(pointcut = "Pointcuts.getOrLoadBy()", throwing = "throwable")
    public void afterThrowingGetOrLoadByAdvice(Throwable throwable){
        log.error(throwable.getMessage());
    }
}
