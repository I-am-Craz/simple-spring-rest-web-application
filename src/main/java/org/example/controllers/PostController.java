package org.example.controllers;

import org.example.entities.Post;
import org.example.entities.User;
import org.example.exceptions.PostNotFoundException;
import org.example.exceptions.UserNotFoundException;
import org.example.services.PostService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.security.Principal;

@Controller
@RequestMapping("/posts")
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @GetMapping
    public String getAllPosts(Model model){
        model.addAttribute("posts",  postService.getAllPost());
        return "posts/posts";
    }

    @GetMapping("/{id}")
    public String getPost(Principal principal, @PathVariable("id") Long id, Model model)
            throws UserNotFoundException, PostNotFoundException {
        Post post = postService.getPostById(id);
        User user = userService.getUserByUsername(principal.getName());
        model.addAttribute("post", post);
        model.addAttribute("user", user);
        return "posts/post";
    }

    @GetMapping("/create")
    public String getCreatingPostPage(@ModelAttribute("post") Post post){
        return "posts/create";
    }

    @PostMapping
    public String createPost(@ModelAttribute("post") Post post, Principal principal)
            throws UserNotFoundException{
        User user = userService.getUserByUsername(principal.getName());
        post.setUser(user);
        postService.savePost(post);
        return "redirect:/posts";
    }

    @GetMapping("/{id}/update")
    public String getEditPage(@PathVariable("id") Long id, Model model) throws PostNotFoundException{
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "posts/update";
    }

    @PatchMapping("/{id}")
    public String updatePost(@ModelAttribute("post") Post post){
        postService.updatePost(post);
        return "redirect:/profile";
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable("id") Long id){
        postService.deletePostById(id);
        return "redirect:/posts";
    }
}
