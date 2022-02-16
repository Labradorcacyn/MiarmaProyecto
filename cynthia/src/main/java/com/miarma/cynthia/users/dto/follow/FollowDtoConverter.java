package com.miarma.cynthia.users.dto.follow;

import com.miarma.cynthia.models.Follow;
import org.springframework.stereotype.Component;

@Component
public class FollowDtoConverter {

    public GetFollowDto convertFollowToGetFollowtDto(Follow follow) {
        return GetFollowDto.builder()
                .idSeguidor(follow.getSeguidor().getId())
                .idSeguido(follow.getSeguido().getId())
                .fullNameSeguidor(follow.getSeguidor().getFullName())
                .fullNameSeguido(follow.getSeguido().getFullName())
                .avatarSeguido(follow.getSeguido().getAvatar())
                .avatarSeguidor(follow.getSeguidor().getAvatar())
                .followRequest(false)
                .build();
    }
}
