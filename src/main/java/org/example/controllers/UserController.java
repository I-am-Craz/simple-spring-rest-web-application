package org.example.controllers;

import org.example.entities.User;
import org.example.exceptions.UserNotFoundException;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String getUsers(Model model, Principal principal) throws UserNotFoundException{
        model.addAttribute("users", userService.getAllUsers());
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("me", user);
        return "users/users";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") Long id, Model model) throws UserNotFoundException{
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "users/user";
    }

    @GetMapping("/me")
    public String getLoggedUser(Model model, Principal principal) throws UserNotFoundException{
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "users/me";
    }

    @PatchMapping("/me/{myId}")
    public String updateLoggedUser(@ModelAttribute("user") User user,
                                   @PathVariable("myId") Long id,
                                   HttpServletRequest request) throws ServletException {
        user.setId(id);
        userService.updateUser(user);
        request.logout();
        return  "redirect:/login";
    }

    @DeleteMapping("/me/{myId}")
    public String deleteLoggedUser(@PathVariable("myId") Long id){
        userService.deleteUserById(id);
        return "redirect:/login";
    }

}
