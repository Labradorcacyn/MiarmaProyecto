package com.miarma.cynthia.users.dto.users;

import lombok.*;

import java.util.Date;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class CreateUserDto {

    private String file;
    private String avatar;
    private Date birthday;
    private String fullName;
    private String email;
    private boolean privacy;
    private String password;
    private String password2;

}
