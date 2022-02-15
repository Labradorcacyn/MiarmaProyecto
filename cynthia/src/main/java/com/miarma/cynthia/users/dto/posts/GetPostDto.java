package com.miarma.cynthia.users.dto.posts;

import com.miarma.cynthia.users.model.UserEntity;
import lombok.*;

import java.util.UUID;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class GetPostDto {

    private Long id;
    private String title;
    private String text;
    private String document;
    private String documentResized;
    private UserEntity user;
    private boolean privacy;
}
