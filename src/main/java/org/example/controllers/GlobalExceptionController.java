package org.example.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionController {
    @ExceptionHandler(Exception.class)
    public ModelAndView handler(Exception exception){
        ModelAndView mav = new ModelAndView();
        mav.addObject("message", exception.getMessage());
        mav.setViewName("error");
        return mav;
    }
}
