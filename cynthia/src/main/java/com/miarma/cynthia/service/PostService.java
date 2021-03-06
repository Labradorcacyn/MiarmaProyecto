package com.miarma.cynthia.service;

import com.miarma.cynthia.models.Post;
import com.miarma.cynthia.repository.PostRepo;
import com.miarma.cynthia.repository.PostRepository;
import com.miarma.cynthia.users.dto.posts.CreatePostDto;
import com.miarma.cynthia.users.dto.posts.GetPostDto;
import com.miarma.cynthia.users.model.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.activation.MimetypesFileTypeMap;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements PostRepository{

    private final PostRepo repository;
    private final FileService fileService;

    @Override
    public Post save(CreatePostDto createPostDto, MultipartFile file, UserEntity user) throws Exception {
        String filename = fileService.store(file);
        String resize = "";
        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        if (new MimetypesFileTypeMap().getContentType(file.getOriginalFilename()).equals("video/x-msvideo")){
            resize = fileService.storeVideoResized(file, 1024);;
        }
        if (new MimetypesFileTypeMap().getContentType(file.getOriginalFilename()).equals("image/jpeg")){
            resize = fileService.storeResized(file, 1024);
        }

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
    public void delete(Post post, String fileName) throws IOException {
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    public List<Post> findPublic(@AuthenticationPrincipal UserEntity user){
       return repository.findByPrivacyFalse();
    }

    @Override
    public Post edit(ResponseEntity<GetPostDto> post, CreatePostDto createPostDto, MultipartFile file) {
        return null;
    }
}