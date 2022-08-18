package org.example.entities;

import lombok.*;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    private List<Post> posts;

    public void setPosts(Set<Post> posts){
        if(this.posts == null){
            this.posts = new ArrayList<>();
        }
        for(Post post : posts){
            post.setUser(this);
            this.posts.add(post);
        }
    }

}
