package com.miarma.cynthia.controller;

import com.miarma.cynthia.models.Post;
import com.miarma.cynthia.repository.PostRepo;
import com.miarma.cynthia.users.dto.posts.CreatePostDto;
import com.miarma.cynthia.service.PostService;
import com.miarma.cynthia.users.dto.posts.GetPostDto;
import com.miarma.cynthia.users.dto.posts.PostDtoConverter;
import com.miarma.cynthia.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;
@RestController
@RequestMapping("/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final PostRepo postRepo;
    private final PostDtoConverter postDtoConverter;

    @PostMapping("/")
    public ResponseEntity<GetPostDto> create(@RequestPart("file") MultipartFile file,
                                             @RequestPart("post") CreatePostDto createPostDto, @AuthenticationPrincipal UserEntity user) throws Exception {
        Post saved = postService.save(createPostDto, file, user);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(postDtoConverter.convertPostToGetPostDto(saved));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPostDto> findPostById(@PathVariable Long id, @AuthenticationPrincipal UserEntity user){
        Optional<Post> post = postRepo.findById(id);
        if(post.isPresent()){
            return ResponseEntity.ok(postDtoConverter.convertPostToGetPostDto(post.get()));
        }else
            return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id){
        return null;
    }
/*
    @PutMapping("/{id}")
    public ResponseEntity<GetPostDto> edit(@PathVariable UUID id, @RequestPart("file") MultipartFile file,
                                             @RequestPart("post") CreatePostDto createPostDto) {

        Optional<Post> post = postService.findById(id);

        if(post.isPresent()){
            Post edit = postService.edit(post, createPostDto, file);

            if (edit == null)
                return ResponseEntity.badRequest().build();
            else
                return ResponseEntity.status(HttpStatus.CREATED).body(postDtoConverter.convertPostToGetPostDto(edit));
            }else{
            return ResponseEntity.notFound().build();
            }
        }*/
}