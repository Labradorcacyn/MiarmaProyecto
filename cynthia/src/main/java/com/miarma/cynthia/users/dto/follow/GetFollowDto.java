package com.miarma.cynthia.users.dto.follow;

import lombok.*;

import java.util.UUID;

@Getter @Setter
@Builder @NoArgsConstructor @AllArgsConstructor
public class GetFollowDto {
    private UUID idSeguidor;
    private UUID idSeguido;
    private String fullNameSeguidor;
    private String fullNameSeguido;
    private String avatarSeguidor;
    private String avatarSeguido;
    private boolean followRequest;
}
