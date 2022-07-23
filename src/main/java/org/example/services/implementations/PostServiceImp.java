package org.example.services.implementations;

import org.example.exceptions.PostNotFoundException;
import org.example.entities.Post;
import org.example.repositories.PostRepository;
import org.example.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
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
            throw new PostNotFoundException("Post with id " + id + " is not found.");
        }
        return optional.get();
    }

    @Override
    public void updatePost(Post post) {
        postRepository.update(post.getTitle(), post.getContent(), post.getImageLink(), post.getId());
    }

    @Override
    public void savePost(Post post) {
        postRepository.save(post);
    }

    @Override
    public void deletePostById(Long id) {
        postRepository.deleteById(id);
    }
}
