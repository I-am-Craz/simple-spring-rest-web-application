package org.example.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class LoggingAspect {
    @Around("Pointcuts.authenticate()")
    public Authentication aroundAuthenticateAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Authentication authentication;
        try{
            authentication = (Authentication) joinPoint.proceed();
            if(authentication == null){
                log.warn("User login error. Wrong username or password. Access denied.");
            }
        }
        catch (Throwable throwable){
            log.error("Authentication failed. " + throwable.getMessage());
            throw throwable;
        }
        return authentication;
    }

    @Around("Pointcuts.signup()")
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
