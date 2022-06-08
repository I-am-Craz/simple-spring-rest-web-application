package org.example.dao;

import org.example.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserDAO {
    private final JdbcTemplate jdbcTemplate;
    private final String tableName = "\"Schema\".users";

    @Autowired
    public UserDAO(JdbcTemplate jdbcTemplate, PostDAO postDAO) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<User> getUsers() {
        String query = String.format("SELECT * FROM %s", this.tableName);
        return jdbcTemplate.query(query, new BeanPropertyRowMapper<>(User.class));
    }

    public User getUser(int id){
        String query = String.format("SELECT * FROM %s WHERE id=?;", this.tableName);
        return jdbcTemplate.query(query, new Object[]{id}, new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
    }

    public User getUser(String email, String password){
        String query = String.format("SELECT * FROM %s WHERE email=? AND password=?;", this.tableName);
        return jdbcTemplate.query(query, new Object[]{email, password}, new BeanPropertyRowMapper<>(User.class))
                .stream().findAny().orElse(null);
    }

    public void createUser(User user){
        String query = String.format("INSERT INTO %s (username, email, password) values (?,?,?);", this.tableName);
        jdbcTemplate.update(query, user.getUsername(), user.getEmail(), user.getPassword());
    }

    public void updateUser(int id, User editedUser){
        String query = String.format("UPDATE %s SET username=?, password=?, email=? where id=?;", this.tableName);
        jdbcTemplate.update(query, editedUser.getUsername(), editedUser.getPassword(), editedUser.getEmail(), id);
    }

    public void deleteUser(int id){
        String query = String.format("DELETE FROM %s WHERE id=?;", this.tableName);
        jdbcTemplate.update(query, id);
    }
}
