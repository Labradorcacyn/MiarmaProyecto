package com.miarma.cynthia.users.dto.posts;

import com.miarma.cynthia.models.Post;
import org.springframework.stereotype.Component;

@Component
public class PostDtoConverter {
    public GetPostDto convertPostToGetPostDto(Post post) {
        return GetPostDto.builder()
                .id(post.getId())
                .title(post.getTitle())
                .user(post.getUser().getFullName())
                .text(post.getText())
                .document(post.getDocument())
                .documentResized(post.getDocumentResized())
                .privacy(post.isPrivacy())
                .build();
    }
}