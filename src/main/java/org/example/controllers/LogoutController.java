package org.example.controllers;

import org.example.models.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/logout")
public class LogoutController {
    @GetMapping
    public String logout(){
        if(User.getLoggedUser() != null){
            User.setLoggedUser(null);
            return "redirect:/login";
        }
        return "redirect:/forbidden";
    }
}
