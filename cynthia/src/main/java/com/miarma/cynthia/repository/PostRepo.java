package com.miarma.cynthia.repository;

import com.miarma.cynthia.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface PostRepo extends JpaRepository<Post, Long> {
    List<Post> findByPrivacyFalse();

    @Query("""
            select u.posts from UserEntity u
            where u.id = :id
            """)
    List<Post> findAllPosts(@Param("id") UUID id);
}
