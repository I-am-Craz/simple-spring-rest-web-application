package org.example.exception_handling;

import org.example.exception_handling.exceptions.PostNotFoundException;
import org.example.exception_handling.exceptions.UserAlreadyExistsException;
import org.example.exception_handling.exceptions.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import java.nio.file.AccessDeniedException;

@Controller
@ControllerAdvice
public class GlobalExceptionController {
    private final String VIEW_NAME = "error";
    private final String INTERNAL_SERVER_ERROR_MESSAGE =
            "There is a problem with the resource you are looking for, and it cannot be displayed.";

    @ExceptionHandler({UserNotFoundException.class, PostNotFoundException.class})
    public ModelAndView notFoundHandler(Exception e){
        return createModelAndView(HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(), e.getMessage());
    }

    @ExceptionHandler({UserAlreadyExistsException.class})
    public ModelAndView AlreadyExistsHandler(Exception e){
        return createModelAndView( HttpStatus.CONTINUE.value(),
                HttpStatus.CONFLICT.getReasonPhrase(), e.getMessage());
    }

    @ExceptionHandler({AccessDeniedException.class})
    public ModelAndView AccessDeniedHandler(Exception e){
        return createModelAndView(HttpStatus.FORBIDDEN.value(),
                HttpStatus.FORBIDDEN.name(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handler(Exception e){
        return createModelAndView( HttpStatus.INTERNAL_SERVER_ERROR.value(),
                HttpStatus.INTERNAL_SERVER_ERROR.name(),
                INTERNAL_SERVER_ERROR_MESSAGE);
    }


    private ModelAndView createModelAndView(int code, String status, String message){
        ModelAndView mav = new ModelAndView();
        mav.addObject("error_code", code);
        mav.addObject("error_status", status);
        mav.addObject("error_message", message);
        mav.setViewName(VIEW_NAME);
        return mav;
    }
}
