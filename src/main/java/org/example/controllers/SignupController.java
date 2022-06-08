package org.example.controllers;

import org.example.models.User;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/signup")
public class SignupController{
    private final UserService userService;

    @Autowired
    public SignupController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public String getSignupPage(Model model){
        model.addAttribute("user", new User());
        return "signup";
    }

    @PostMapping
    public String signup(@RequestParam("username") String username,
                         @RequestParam("email") String email,
                         @RequestParam("password") String password){
        userService.createUser(new User(username.trim(), email.trim(), password.trim()));
        User.setLoggedUser(userService.getUser(email, password));
        return "redirect:/posts";
    }
}
