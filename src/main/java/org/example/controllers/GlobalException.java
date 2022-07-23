package org.example.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice(basePackages = {"org.example.controllers"})
public class GlobalException {
    @ExceptionHandler(Exception.class)
    public ModelAndView handler(HttpServletRequest request, Exception exception) throws Exception {
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", exception.getMessage());
        mav.setViewName("error");
        return mav;
    }
}
