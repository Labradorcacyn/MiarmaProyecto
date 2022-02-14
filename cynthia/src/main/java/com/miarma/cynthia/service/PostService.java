package com.miarma.cynthia.service;

import com.miarma.cynthia.models.Post;
import com.miarma.cynthia.repository.PostRepo;
import com.miarma.cynthia.repository.PostRepository;
import com.miarma.cynthia.users.dto.posts.CreatePostDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService implements PostRepository {

    private final PostRepo repository;
    private final FileService fileService;

    @Override
    public Post save(CreatePostDto createPostDto, MultipartFile file) {
        String filename = fileService.store(file);

        String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/download/")
                .path(filename)
                .toUriString();

        return repository.save(Post.builder()
                .isPrivate(createPostDto.isPrivate())
                .text(createPostDto.getText())
                .title(createPostDto.getTitle())
                .document(uri)
                .build());
    }

    @Override
    public List<Post> findAll() {
        return repository.findAll();
    }
}