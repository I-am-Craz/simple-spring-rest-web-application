package org.example.models;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    private int id;
    private String title;
    private String content;
    private int creatorId;
    private String image;

    public Post(String title, String content, int creatorId, String image){
        this.title = title;
        this.content = content;
        this.creatorId = creatorId;
        this.image = image;
    }
}
