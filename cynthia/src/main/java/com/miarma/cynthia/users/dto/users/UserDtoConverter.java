package com.miarma.cynthia.users.dto.users;

import com.miarma.cynthia.users.model.UserEntity;
import org.springframework.stereotype.Component;

@Component
public class UserDtoConverter {

    public GetUserDto convertUserEntityToGetUserDto(UserEntity user) {
        return GetUserDto.builder()
                .avatar(user.getAvatar())
                .birthday(user.getBirthday())
                .fullName(user.getFullName())
                .email(user.getEmail())
                .privacy(user.isPrivacy())
                .role(user.getRole().name())
                .build();


    }

}
