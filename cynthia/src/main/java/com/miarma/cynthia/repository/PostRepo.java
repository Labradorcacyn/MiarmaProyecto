package com.miarma.cynthia.repository;

import com.miarma.cynthia.models.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepo extends JpaRepository<Post, Long> {
}
