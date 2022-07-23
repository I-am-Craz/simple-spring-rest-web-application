package org.example.controllers;

import org.example.entities.Role;
import org.example.entities.User;
import org.example.exceptions.UserAlreadyExistsException;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Collections;

@Controller
@RequestMapping("/signup")
public class SignupController{
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping
    public String getSignupPage(@ModelAttribute("user") User user){
        return "signup";
    }

    @PostMapping
    public String signup(@ModelAttribute("user") User user, HttpServletRequest request)
            throws ServletException, UserAlreadyExistsException {
        char[] password = user.getPassword().toCharArray();
        user.setRoles(Collections.singleton(Role.USER));
        userService.saveUser(user);
        request.login(user.getUsername(), String.valueOf(password));
        Arrays.fill(password, '0');
        return "redirect:/posts";
    }
}
