package org.example.services;

import org.example.exceptions.UserNotFoundException;
import org.example.entities.User;

import java.util.List;
public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id) throws UserNotFoundException;
    User getUserByUsername(String username) throws UserNotFoundException;
    void updateUser(User user);
    User saveUser(User user);
    void deleteUserById(Long id);
}
