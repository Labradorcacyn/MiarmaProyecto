package com.miarma.cynthia.users.dto.posts;

import lombok.*;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class GetPostDto {

    private Long id;
    private String title;
    private String text;
    private String document;
    private String documentResized;
    private String user;
    private boolean privacy;
}
