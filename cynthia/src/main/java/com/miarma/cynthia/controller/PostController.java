package com.miarma.cynthia.controller;

import com.miarma.cynthia.models.Post;
import com.miarma.cynthia.repository.PostRepo;
import com.miarma.cynthia.users.dto.posts.CreatePostDto;
import com.miarma.cynthia.service.PostService;
import com.miarma.cynthia.users.dto.posts.GetPostDto;
import com.miarma.cynthia.users.dto.posts.PostDtoConverter;
import com.miarma.cynthia.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @GetMapping("/public")
    public ResponseEntity<List<GetPostDto>> findPublicPost(){
        if(postRepo.findByPrivacyFalse().isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(postRepo.findByPrivacyFalse().stream().map(p->postDtoConverter.convertPostToGetPostDto(p)).collect(Collectors.toList()));
    }

    @GetMapping("/me")
    public ResponseEntity<List<GetPostDto>> findMyPost(@AuthenticationPrincipal UserEntity user){
        return ResponseEntity.ok().body(postRepo.findAllPosts(user.getId()).stream().map(p->postDtoConverter.convertPostToGetPostDto(p)).collect(Collectors.toList()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id, @RequestPart("file") MultipartFile file,
                                    @RequestPart("post") CreatePostDto createPostDto) throws ChangeSetPersister.NotFoundException, IOException {
        Post post = postRepo.findById(id).orElseThrow(ChangeSetPersister.NotFoundException::new);
        postService.delete(post, file);
        return ResponseEntity.noContent().build();
    }

    /*@PutMapping("/{id}")
    public ResponseEntity<GetPostDto> edit(@PathVariable Long id, @RequestPart("file") MultipartFile file,
                                             @RequestPart("post") CreatePostDto createPostDto) {
        Post post = postService.findPostById(id);

        if(post == null){
            return ResponseEntity.notFound().build();
        }else{
         //Post edit = postService.edit(post, createPostDto, file);
            return ResponseEntity.status(HttpStatus.CREATED).body(postDtoConverter.convertPostToGetPostDto(edit));
        }
    }*/
}