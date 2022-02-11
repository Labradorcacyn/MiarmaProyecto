package com.miarma.cynthia.users.dto;

import com.miarma.cynthia.users.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public GetUserDto convertUserEntityToGetUserDto(UserEntity user) {
        return GetUserDto.builder()
                .avatar(user.getAvatar())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .isPrivate(user.getIsPrivate())
                .role(user.getRole().name())
                .build();


    }

}
