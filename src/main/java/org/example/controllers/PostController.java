package org.example.controllers;

import org.example.entities.Post;
import org.example.entities.User;
import org.example.exception_handling.exceptions.PostNotFoundException;
import org.example.exception_handling.exceptions.UserNotFoundException;
import org.example.services.PostService;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return "posts/all";
    }

    @GetMapping("/{id}")
    public String getPost(@AuthenticationPrincipal(expression = "user") User user,
                          @PathVariable("id") Long id, Model model)
            throws UserNotFoundException, PostNotFoundException {
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        model.addAttribute("user", user);
        return "posts/post";
    }

    @GetMapping("/create")
    public String getCreatingPostPage(@ModelAttribute("post") Post post){
        return "posts/create";
    }

    @PostMapping
    public String createPost(@ModelAttribute("post") Post post,
                             @AuthenticationPrincipal(expression = "user") User user)
            throws UserNotFoundException{
        post.setUser(user);
        post.setEnabled(true);
        postService.savePost(post);
        return "redirect:/posts";
    }

    @GetMapping("/{id}/update")
    public String getEditPage(@PathVariable("id") Long id, Model model)
            throws PostNotFoundException{
        Post post = postService.getPostById(id);
        model.addAttribute("post", post);
        return "posts/update";
    }

    @PatchMapping("/{id}")
    public String updatePost(@ModelAttribute("post") Post post,
                             @PathVariable("id") Long id,
                             @AuthenticationPrincipal(expression = "user") User user){
        post.setId(id);
        post.setUser(user);
        postService.savePost(post);
        return "redirect:/profile";
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable("id") Long id){
        postService.deletePostById(id);
        return "redirect:/posts";
    }

    @PatchMapping("/{id}/block")
    public String blockPost(@PathVariable("id") Long id){
        postService.blockPostById(id);
        return "redirect:/posts";
    }
}
