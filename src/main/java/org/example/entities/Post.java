package org.example.entities;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name = "posts")
@Setter
@Getter
@NoArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @ManyToOne(targetEntity = User.class)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "image_link")
    private String imageLink;

    @Column(name = "enabled")
    private boolean isEnabled;
}
