package org.example.controllers;

import org.example.entities.User;
import org.example.exceptions.UserNotFoundException;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
@Controller
@RequestMapping("/profile")
public class ProfileController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String getProfilePage(Model model, Principal principal) throws UserNotFoundException {
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "profile";
    }
}
