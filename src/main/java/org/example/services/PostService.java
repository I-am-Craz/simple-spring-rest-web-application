package org.example.services;

import org.example.exception_handling.exceptions.PostNotFoundException;
import org.example.entities.Post;
import java.util.List;

public interface PostService {
    List<Post> getAllPost();
    Post getPostById(Long id) throws PostNotFoundException;
    Post savePost(Post post);
    void deletePostById(Long id);

    void blockPostById(Long id);
}
