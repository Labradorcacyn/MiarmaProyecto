package com.miarma.cynthia.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {

    private String username;
    private String avatar;
    private String fullname;
    private String email;
    private Boolean isPrivate;
    private String password;
    private String password2;

}
