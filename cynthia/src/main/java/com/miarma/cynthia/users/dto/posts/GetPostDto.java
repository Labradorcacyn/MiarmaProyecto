package com.miarma.cynthia.users.dto.posts;

import lombok.*;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class GetPostDto {

    private String title;
    private String text;
    private String document;
    private boolean isPrivate;
}
