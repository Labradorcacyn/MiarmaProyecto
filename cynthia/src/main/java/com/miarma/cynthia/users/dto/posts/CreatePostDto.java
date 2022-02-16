package com.miarma.cynthia.users.dto.posts;

import com.miarma.cynthia.users.model.UserEntity;
import lombok.*;

import javax.persistence.Lob;
import javax.validation.constraints.NotNull;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class CreatePostDto {

    @NotNull
    private String title;

    @NotNull
    @Lob
    private String text;

    @NotNull
    private String document;

    private String documentResized;

    private String user;

    private boolean privacy;
}
