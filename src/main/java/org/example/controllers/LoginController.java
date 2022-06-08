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
@RequestMapping("/login")
public class LoginController{
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public String getLoginPage(Model model){
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping
    public String login(@RequestParam("email") String email,
                        @RequestParam("password") String password,
                        Model model){
        User user = userService.getUser(email, password);

        if(user != null && user.getId() != 0 &&
           user.getUsername() != null &&
           user.getEmail() != null &&
           user.getPassword() != null){
           User.setLoggedUser(user);
           model.addAttribute("user", user);
           return  "redirect:/posts";
        }

        return "errors/userNotFound";
    }
}
