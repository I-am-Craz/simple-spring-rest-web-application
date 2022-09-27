package org.example.services;

import org.example.exception_handling.exceptions.UserNotFoundException;
import org.example.entities.User;
import java.util.List;

public interface UserService {
    List<User> getAllUsers();
    User getUserById(Long id) throws UserNotFoundException;
    User getUserByUsername(String username) throws UserNotFoundException;
    User saveUser(User user);
    void deleteUserById(Long id);
}
