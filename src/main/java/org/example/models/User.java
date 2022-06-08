package org.example.models;

import lombok.*;

import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    private static User loggedUser;

    private int id;
    private String username;
    private String password;
    private String email;
    private List<Post> posts;

    public User(String username, String email, String password){
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User(String email, String password){
        this.email = email;
        this.password = password;
    }

    public static User getLoggedUser(){
        return loggedUser;
    }

    public static void setLoggedUser(User user){
        loggedUser = user;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj) return true;
        if(obj == null || this.getClass() != obj.getClass()) return false;
        User user = (User) obj;
        if(this.getId() == user.getId() &&
                Objects.equals(this.getUsername(), user.getUsername()) &&
                Objects.equals(this.getPassword(), user.getPassword()) &&
                Objects.equals(this.getEmail(), user.getEmail())){
            return true;
        } else{
            return false;
        }
    }

    @Override
    public int hashCode(){
        return Objects.hash(this.getId(), this.getUsername(), this.getPassword(), this.getEmail());
    }

    public List<String> getRoles() {
        return null;
    }
}
