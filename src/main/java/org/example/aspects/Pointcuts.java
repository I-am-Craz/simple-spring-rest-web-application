package org.example.aspects;

import org.aspectj.lang.annotation.Pointcut;

public class Pointcuts {
    @Pointcut("execution(* org.example.config.UserAuthenticationProvider.authenticate(..))")
    public void authenticate(){}

    @Pointcut("execution(String org.example.controllers.SignupController.signup(..))")
    public void signup(){}

    @Pointcut("execution(* org.example.services.implementations.*.*By*(..))")
    public void getOrLoadBy(){}

    @Pointcut("execution(org.example.entities.User org.example.services.implementations.UserServiceImp.saveUser(org.example.entities.User))")
    public void saveUser(){}

    @Pointcut("execution(* org.example.config.JpaConfig.dataSource())")
    public void dataSource(){}
}
