package org.example.controllers;

import org.example.dao.PostDAO;
import org.example.models.Post;
import org.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/posts")
public class PostController {
    private PostDAO postDAO;

    @Autowired
    public PostController(PostDAO postDAO){
        this.postDAO = postDAO;
    }

    @GetMapping
    public String getAllPosts(Model model){
        if(User.getLoggedUser() == null){
            return "redirect:/forbidden";
        }

        List<Post> posts = new ArrayList<>();

        for(Post post: postDAO.getAllPosts()){
            if(post.getContent().length()>50){
                post.setContent(post.getContent().substring(50)+"...");
                posts.add(post);
            }
        }

        model.addAttribute("posts", posts);
        return "posts/posts";
    }

    @GetMapping("/{id}")
    public String getPost(@PathVariable("id") int id, Model model){
        if(User.getLoggedUser() == null){
            return "redirect:/forbidden";
        }
        Post post = postDAO.getPost(id);
        if(post != null){
            model.addAttribute("post", post);
            model.addAttribute("user", User.getLoggedUser());
            return "posts/post";
        }
        return "redirect:/postNotFound";
    }

    @GetMapping("/create")
    public String getCreatingPostPage(Model model){
        if(User.getLoggedUser() == null){
            return "redirect:/forbidden";
        }
        model.addAttribute("post", new Post());
        return "posts/create";
    }

    @PostMapping
    public String createPost(@RequestParam("title") String title,
                             @RequestParam("content") String content,
                             @RequestParam("image") String image){
        postDAO.createPost(new Post(title, content, User.getLoggedUser().getId(), image));
        return "redirect:/posts";
    }

    @GetMapping("/{id}/update")
    public String getEditPage(@PathVariable("id") int id, Model model){
        if(User.getLoggedUser() == null){
            return "redirect:/forbidden";
        }
        Post post = postDAO.getPost(id);
        if(post != null){
            model.addAttribute("post", post);
            return "posts/update";
        }
        return "redirect:/postNotFound";
    }

    @PatchMapping("/{id}")
    public String updatePost(@PathVariable("id") int id,
                           @RequestParam("title") String title,
                           @RequestParam("content") String content,
                           @RequestParam("image") String image){
        postDAO.updatePost(id, new Post(title, content, User.getLoggedUser().getId(), image));
        return "redirect:/posts";
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable("id") int id){
        postDAO.deletePost(id);
        return "redirect:/posts";
    }

}
