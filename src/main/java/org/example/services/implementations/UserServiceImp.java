package org.example.services.implementations;

import org.example.entities.Post;
import org.example.exception_handling.exceptions.UserAlreadyExistsException;
import org.example.exception_handling.exceptions.UserNotFoundException;
import org.example.entities.User;
import org.example.repositories.UserRepository;
import org.example.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@EnableAspectJAutoProxy(proxyTargetClass=true)
public class UserServiceImp implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) throws UserNotFoundException {
        Optional<User> optional = userRepository.findById(id);
        if(optional.isEmpty()){
            throw new UserNotFoundException("User with the ID " + id + " is not found.");
        }
        return optional.get();
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with the username " + username + " is not found.");
        }
        return optionalUser.get();
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        try{
            if(getUserByUsername(user.getUsername()) != null){
                throw new UserAlreadyExistsException("User with the username " +
                        user.getUsername() + " already exists.");
            }
        } catch (UserNotFoundException ignored){}
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }

    @Override
    public void blockUserById(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            throw new UserNotFoundException("User with the ID " + id + " is not found.");
        }
        User user = optionalUser.get();
        if(user.isEnabled()){
            user.setEnabled(false);
            for(Post post  : user.getPosts()){
                post.setEnabled(false);
            }
        } else{
            user.setEnabled(true);
            for(Post post  : user.getPosts()){
                post.setEnabled(true);
            }
        }
    }
}
