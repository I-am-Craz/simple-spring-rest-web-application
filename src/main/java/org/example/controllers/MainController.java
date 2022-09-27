package org.example.controllers;

import org.example.entities.User;
import org.example.exception_handling.exceptions.UserNotFoundException;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

@Controller
public class MainController {
    @Autowired
    private UserService userService;

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
    public String getProfilePage(Model model, Principal principal) throws UserNotFoundException {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "users/profile";
    }

    @GetMapping("/signup")
    public String getSignupPage(@ModelAttribute("user") User user){
        return "users/create";
    }
}