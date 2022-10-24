package org.example.services.implementations;

import org.example.exception_handling.exceptions.PostNotFoundException;
import org.example.entities.Post;
import org.example.exception_handling.exceptions.UserNotFoundException;
import org.example.repositories.PostRepository;
import org.example.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class PostServiceImp implements PostService {
    @Autowired
    private PostRepository postRepository;

    @Override
    @Transactional
    public List<Post> getAllPost() {
        return (List<Post>) postRepository.findAll();
    }

    @Override
    public Post getPostById(Long id) throws PostNotFoundException {
        Optional<Post> optional = postRepository.findById(id);
        if(optional.isEmpty()){
            throw new PostNotFoundException("Post with the ID " + id + " is not found.");
        }
        return optional.get();
    }

    @Override
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }

    @Override
    public void blockPostById(Long id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (optionalPost.isEmpty()) {
            throw new UserNotFoundException("User with the ID " + id + " is not found.");
        }
        Post post  = optionalPost.get();
        post.setEnabled(!post.isEnabled());
    }
}
