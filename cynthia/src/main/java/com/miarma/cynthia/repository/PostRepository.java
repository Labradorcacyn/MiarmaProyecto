package com.miarma.cynthia.repository;

import com.miarma.cynthia.models.Post;
import com.miarma.cynthia.users.dto.posts.CreatePostDto;
import com.miarma.cynthia.users.dto.posts.GetPostDto;
import com.miarma.cynthia.users.model.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

public interface PostRepository {
    Post save(CreatePostDto createPostDto, MultipartFile file, UserEntity user) throws Exception;
    void delete(Post post, String fileName) throws IOException;
    Post edit(ResponseEntity<GetPostDto> post, CreatePostDto createPostDto, MultipartFile file);
}