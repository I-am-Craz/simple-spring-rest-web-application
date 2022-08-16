package org.example.aspects;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
@Aspect
@Slf4j
public class LoggingAspect {
    @Around("Pointcuts.authenticate()")
    public Authentication aroundAuthenticateAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        Authentication authentication = null;
        try{
            authentication = (Authentication) joinPoint.proceed();
            log.info("User authenticated successfully.");
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

    @AfterThrowing(pointcut = "Pointcuts.saveUser()", throwing = "throwable")
    public void afterThrowingSaveUserAdvice(Throwable throwable){
        log.error(throwable.getMessage());
    }

    @Around("Pointcuts.dataSource()")
    public DataSource aroundDataSourceAdvice(ProceedingJoinPoint joinPoint) throws Throwable {
        DataSource dataSource = null;
        try{
            dataSource = (DataSource) joinPoint.proceed();
            log.info("DataSource is running successfully.");
        }
        catch (Throwable throwable){
            log.error("DataSource working error. " + throwable.getMessage());
            throw throwable;
        }
        return dataSource;
    }
}
