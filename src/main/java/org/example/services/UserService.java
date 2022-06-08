package org.example.services;

import lombok.Getter;
import org.example.dao.PostDAO;
import org.example.dao.UserDAO;
import org.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.*;

@Service
public class UserService{
    private final UserDAO userDAO;
    private final PostDAO postDAO;

    @Autowired
    public UserService(UserDAO userDAO, PostDAO postDAO){
        this.userDAO = userDAO;
        this.postDAO = postDAO;
    }

    public List<User> getUsers(){
        return userDAO.getUsers();
    }

    public User getUser(int id){
        User user = userDAO.getUser(id);
        if(user != null){
            user.setPassword(encryptUserPassword(user.getPassword()));
            user.setPosts(postDAO.getPostsByCreator(id));
            return user;
        }
        return new User();
    }

    public User getUser(String email, String password){
        User user =  userDAO.getUser(email, encryptUserPassword(password));
        if(user != null){
            user.setPosts(postDAO.getPostsByCreator(user.getId()));
            return user;
        }
        return new User();
    }

    public void updateUser(int id, User user){
        user.setPassword(encryptUserPassword(user.getPassword()));
        userDAO.updateUser(id, user);
    }

    public void createUser(User user){
        user.setPassword(encryptUserPassword(user.getPassword()));
        user.setPosts(new ArrayList<>());
        userDAO.createUser(user);
    }

    public void deleteUser(int id){
        userDAO.deleteUser(id);
    }

    private String encryptUserPassword(String password){
        byte[] securePassword = Encryptor.hash(password.toCharArray(), Encryptor.getSalt().getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(securePassword);
    }

    private static class Encryptor{
        @Getter
        private static final String salt = "baz4IplnbU8FIy1reKk1rRFbvzG6fQ";
        private static final int iterationsCount = 10_000;
        private static final int keyLength = 60;

        public static byte[] hash(char[] password, byte[] salt){
            PBEKeySpec spec = new PBEKeySpec(password, salt, iterationsCount, keyLength);
            Arrays.fill(password, Character.MAX_VALUE);
            try{
                var secretKeyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
                return secretKeyFactory.generateSecret(spec).getEncoded();
            }
            catch (NoSuchAlgorithmException | InvalidKeySpecException e){
                throw new AssertionError("Error while hashing a password:" + e.getMessage(), e);
            }
            finally {
                spec.clearPassword();
            }
        }

    }
}
