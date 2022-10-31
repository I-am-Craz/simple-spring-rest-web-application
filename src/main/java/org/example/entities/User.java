package org.example.entities;

import lombok.*;
import org.hibernate.annotations.Type;
import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "email")
    private String email;

    @Column(name = "roles", columnDefinition = "roles[]")
    @Type(type = "org.example.custom_hibernate_data_types.RolesArrayType")
    private String[] roles;

    @Column(name = "enabled")
    private boolean isEnabled;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
    private List<Post> posts;

    public void setPosts(List<Post> posts){
        if(this.posts == null){
            this.posts = new ArrayList<>();
        }
        for(Post post : posts){
            post.setUser(this);
            this.posts.add(post);
        }
    }
}
