package org.example.services.implementations;

import org.example.exceptions.UserAlreadyExistsException;
import org.example.exceptions.UserNotFoundException;
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
            throw new UserNotFoundException("User with id " + id + " is not found.");
        }
        return optional.get();
    }

    @Override
    public User getUserByUsername(String username) throws UserNotFoundException{
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User with username " + username + " is not found.");
        }
        return optionalUser.get();
    }

    @Override
    public void updateUser(User user) {
        userRepository.update(user.getUsername(), passwordEncoder.encode(user.getPassword()),
                user.getEmail(), user.getId());
    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistsException {
        if(getUserByUsername(user.getUsername()) != null){
            throw new UserAlreadyExistsException("User with the username " +
                      user.getUsername() + " already exists.");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id){
        userRepository.deleteById(id);
    }
}
