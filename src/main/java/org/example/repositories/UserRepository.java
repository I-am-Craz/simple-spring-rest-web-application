package org.example.repositories;

import org.example.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    @Modifying
    @Query(value = "UPDATE users SET username=?1, password=?2, email=?3 WHERE user_id=?4", nativeQuery = true)
    void update(String username, String password, String email, Long id);
}
