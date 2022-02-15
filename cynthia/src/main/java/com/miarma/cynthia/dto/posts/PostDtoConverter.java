package com.miarma.cynthia.users.dto.posts;

import com.miarma.cynthia.models.Post;
import org.springframework.stereotype.Component;

@Component
public class PostDtoConverter {
    public GetPostDto convertPostToGetPostDto(Post post) {
        return GetPostDto.builder()
                .title(post.getTitle())
                .text(post.getText())
                .document(post.getDocument())
                .isPrivate(post.isPrivate())
                .build();
    }
}