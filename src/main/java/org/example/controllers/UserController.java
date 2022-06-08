package org.example.controllers;

import org.example.models.User;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/users")
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public String getUsers(Model model){
        if(User.getLoggedUser() == null){
            return "redirect:/forbidden";
        }

        model.addAttribute("users", userService.getUsers());
        model.addAttribute("me", User.getLoggedUser());
        return "users/users";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") int id, Model model){
        if(User.getLoggedUser() == null){
            return "redirect:/forbidden";
        }

        User user = userService.getUser(id);
        if(user != null && user.getId() != 0 &&
            user.getUsername() != null &&
            user.getEmail() != null &&
            user.getPassword() != null){
            model.addAttribute("user", user);
            return "users/user";
        }
        return "redirect:/userNotFound";
    }

    @GetMapping("/me")
    public String getLoggedUser(Model model){
        if(User.getLoggedUser() == null){
            return "redirect:/forbidden";
        }

        User me = User.getLoggedUser();
        model.addAttribute("user", me);
        return "users/me";
    }

    @PatchMapping("/{id}")
    public String updateLoggedUser(@PathVariable("id") int id,
                                   @RequestParam("username") String username,
                                   @RequestParam("email") String email,
                                   @RequestParam("password") String password){
        User user = userService.getUser(id);
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(password);
        userService.updateUser(id, user);
        User.setLoggedUser(null);
        return  "redirect:/login";
    }

    @DeleteMapping("/{id}")
    public String deleteLoggedUser(@PathVariable("id") int id){
        userService.deleteUser(id);
        User.setLoggedUser(null);
        return "redirect:/login";
    }

}
