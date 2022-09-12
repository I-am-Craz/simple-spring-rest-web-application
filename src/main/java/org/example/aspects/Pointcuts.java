package org.example.aspects;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("execution(* org.example.config.UserAuthenticationProvider.authenticate(..))")
    public void authenticate(){}

    @Pointcut("execution(String org.example.controllers.UserController.createNewUser(..))")
    public void createNewUser(){}

    @Pointcut("execution(* org.example.services.implementations.*.*By*(..))")
    public void getOrLoadBy(){}
}
