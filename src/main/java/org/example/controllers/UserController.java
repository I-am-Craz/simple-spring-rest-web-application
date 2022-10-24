package org.example.controllers;

import org.example.entities.Role;
import org.example.entities.User;
import org.example.exception_handling.exceptions.UserAlreadyExistsException;
import org.example.exception_handling.exceptions.UserNotFoundException;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public String getUsers(Model model, @AuthenticationPrincipal(expression = "user") User user)
            throws UserNotFoundException{
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("me", user);
        return "users/all";
    }

    @GetMapping("/{id}")
    public String getUser(@PathVariable("id") Long id, Model model) throws UserNotFoundException{
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        return "users/user";
    }

    @PostMapping("/create")
    public String createNewUser(@ModelAttribute("user") User user, HttpServletRequest request)
        throws ServletException, UserAlreadyExistsException {

        char[] password = user.getPassword().toCharArray();

        user.setRoles(new String[]{Role.ROLE_USER.name()});
        user.setEnabled(true);

        userService.saveUser(user);
        request.login(user.getUsername(), String.valueOf(password));

        Arrays.fill(password, '0');
        user.setPassword(String.valueOf(password));
        return "redirect:/posts";
    }

    @PatchMapping("/{id}")
    public String updateLoggedUser(@ModelAttribute("user") User user,
                                   @PathVariable("id") Long id,
                                   HttpServletRequest request) throws ServletException {
        user.setId(id);
        userService.saveUser(user);
        request.logout();
        return  "redirect:/login";
    }

    @DeleteMapping("/{id}")
    public String deleteLoggedUser(@PathVariable("id") Long id){
        userService.deleteUserById(id);
        return "redirect:/login";
    }

    @PatchMapping("/{id}/block")
    public String blockUser(@PathVariable("id") Long id){
        userService.blockUserById(id);
        return "redirect:/users";
    }
}
