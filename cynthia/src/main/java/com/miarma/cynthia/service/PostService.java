package com.miarma.cynthia.service;

import com.miarma.cynthia.models.Post;
import com.miarma.cynthia.repository.PostRepo;
import com.miarma.cynthia.repository.PostRepository;
import com.miarma.cynthia.users.dto.posts.CreatePostDto;
import com.miarma.cynthia.users.dto.posts.GetPostDto;
import com.miarma.cynthia.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class PostService implements PostRepository{

    private final PostRepo repository;
    private final FileService fileService;

    public Post findPostById(Long id){
        return repository.findById(id).get();
    }

    @Override
    public Post save(CreatePostDto createPostDto, MultipartFile file, UserEntity user) throws Exception {
        String filename = fileService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        String resize = fileService.storeResized(file, 1024);

        String uriResized = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(resize)
                .toUriString();

        Post post = Post.builder()
                .privacy(createPostDto.isPrivacy())
                .text(createPostDto.getText())
                .title(createPostDto.getTitle())
                .document(uri)
                .documentResized(uriResized)
                .build();

        post.addUSer(user);

        return repository.save(post);
    }

    @Override
    public void delete(Post post) throws IOException {
        repository.delete(post);
        fileService.deleteFile(post.getDocument());
    }

    @Override
    public Post edit(ResponseEntity<GetPostDto> post, CreatePostDto createPostDto, MultipartFile file) {
        return null;
    }
}