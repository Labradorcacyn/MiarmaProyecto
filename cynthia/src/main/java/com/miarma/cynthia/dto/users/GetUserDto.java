package com.miarma.cynthia.users.dto.users;

import lombok.*;

import java.awt.*;
import java.util.Date;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class GetUserDto {

    private String avatar;
    private String file;
    private Date birthday;
    private String fullName;
    private String email;
    private boolean privacy;
    private String role;



}
