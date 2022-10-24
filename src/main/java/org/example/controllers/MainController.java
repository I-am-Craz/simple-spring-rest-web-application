package org.example.controllers;

import lombok.extern.slf4j.Slf4j;
import org.example.entities.User;
import org.example.exception_handling.exceptions.UserNotFoundException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@Slf4j
public class MainController {
    @GetMapping("/")
    public String homePage(){
        return "index";
    }

    @GetMapping("/login")
    public String getLoginPage(@ModelAttribute User user){
        return "login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth != null){
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String getProfilePage(Model model,
                                 @AuthenticationPrincipal(expression = "user") User user)
            throws UserNotFoundException {
        model.addAttribute("user", user);
        return "users/profile";
    }

    @GetMapping("/signup")
    public String getSignupPage(@ModelAttribute("user") User user){
        return "users/create";
    }
}