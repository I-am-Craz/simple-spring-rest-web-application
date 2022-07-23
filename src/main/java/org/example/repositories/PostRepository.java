package org.example.repositories;

import org.example.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> getPostsByUserId(Long userId);
    @Modifying
    @Query(value = "UPDATE posts SET title=?1, content=?2, image_link=?3 WHERE post_id=?4", nativeQuery = true)
    void update(String title, String content, String image_link, Long id);
}
