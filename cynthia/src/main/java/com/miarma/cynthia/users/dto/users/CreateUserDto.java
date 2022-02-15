package com.miarma.cynthia.users.dto.users;

import lombok.*;
import java.time.LocalDate;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class CreateUserDto {

    private String avatar;
    private LocalDate birthday;
    private String fullName;
    private String email;
    private boolean privacy;
    private String password;
    private String password2;

}
