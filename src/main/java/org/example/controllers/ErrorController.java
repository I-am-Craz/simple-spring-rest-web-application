package org.example.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ErrorController {
    @GetMapping("**")
    public String getNotFoundPageError(){
        return "errors/pageNotFound";
    }

    @GetMapping("/forbidden")
    public String getForbiddenPageError(){
        return "errors/forbidden";
    }

    @GetMapping("/userNotFound")
    public String getUserNotFoundPage(){
        return "errors/userNotFound";
    }

    @GetMapping("/postNotFound")
    public String getPostNotFoundPage(){
        return "errors/postNotFound";
    }
}
