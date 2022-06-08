package org.example.controllers;

import org.example.models.User;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/profile")
public class ProfileController {
    private UserService userService;

    @Autowired
    public ProfileController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public String getProfilePage(Model model){
        User user = userService.getUser(User.getLoggedUser().getId());
        if(user != null){
            model.addAttribute("user", user);
            return "profile";
        }
        return "redirect:/forbidden";
    }
}
