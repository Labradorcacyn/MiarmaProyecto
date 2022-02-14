package com.miarma.cynthia.users.dto;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateUserDto {

    private String avatar;
    private Date birthday;
    private String fullname;
    private String email;
    private Boolean isPrivate;
    private String password;
    private String password2;

}
