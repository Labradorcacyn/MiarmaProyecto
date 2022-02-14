package com.miarma.cynthia.repository;

import com.miarma.cynthia.models.Post;
import com.miarma.cynthia.users.dto.posts.CreatePostDto;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostRepository {
    Post save(CreatePostDto createPostDto, MultipartFile file);
    List<Post> findAll();
}