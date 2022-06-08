package org.example.dao;

import org.example.models.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PostDAO {
    private final JdbcTemplate jdbcTemplate;
    private final String tableName = "\"Schema\".posts";

    @Autowired
    public PostDAO(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Post> getAllPosts(){
        String query = String.format("SELECT * FROM %s", this.tableName);
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<Post>(Post.class));
    }

    public Post getPost(int id){
        String query = String.format("SELECT * FROM %s WHERE id=?", this.tableName);
        return jdbcTemplate.query(query, new Object[]{id}, new BeanPropertyRowMapper<Post>(Post.class))
                .stream().findAny().orElse(null);
    }

    public List<Post> getPostsByCreator(int creatorId){
        String query = String.format("SELECT * FROM %s WHERE creator_id=?", this.tableName);
        return jdbcTemplate.query(query, new Object[]{creatorId}, new BeanPropertyRowMapper<Post>(Post.class));
    }

    public void createPost(Post post){
        String query = String.format("INSERT INTO %s (title, content, creator_id, image) VALUES(?,?,?,?);", tableName);
        jdbcTemplate.update(query, post.getTitle(), post.getContent(), post.getCreatorId(), post.getImage());
    }

    public void updatePost(int id, Post post){
        String query = String.format("UPDATE %s SET title=?, content=?, image=? WHERE id=?;", this.tableName);
        jdbcTemplate.update(query, post.getTitle(), post.getContent(), post.getImage(), id);
    }

    public void deletePost(int id){
        String query = String.format("DELETE FROM %s WHERE id=?", this.tableName);
        jdbcTemplate.update(query, id);
    }
}

