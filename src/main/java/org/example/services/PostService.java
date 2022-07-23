package org.example.services;

import org.example.exceptions.PostNotFoundException;
import org.example.entities.Post;
import java.util.List;

public interface PostService {
    List<Post> getAllPost();
    Post getPostById(Long id) throws PostNotFoundException;
    void updatePost(Post post);
    void savePost(Post post);
    void deletePostById(Long id);
}
