package org.example.services.implementations;

import org.example.entities.CustomUserDetails;
import org.example.entities.User;
import org.example.exception_handling.exceptions.UserNotFoundException;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if(optionalUser.isEmpty()){
            throw new UserNotFoundException("User with the username " + username + " is not found.");
        }
        return new CustomUserDetails(optionalUser.get());
    }
}
