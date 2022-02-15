package com.miarma.cynthia.repository;

import com.miarma.cynthia.models.Post;
import com.miarma.cynthia.users.dto.posts.CreatePostDto;
import com.miarma.cynthia.users.model.UserEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

public interface PostRepository {
    Post save(CreatePostDto createPostDto, MultipartFile file, UserEntity user) throws Exception;
    void delete(Post post) throws IOException;
    Post edit(Optional<Post> post, CreatePostDto createPostDto, MultipartFile file);
    //Optional<Post> findById(UUID id);
}