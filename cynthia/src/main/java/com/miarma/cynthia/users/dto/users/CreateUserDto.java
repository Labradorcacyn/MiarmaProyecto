package com.miarma.cynthia.users.dto.users;

import com.miarma.cynthia.exception.annotations.UniqueName;
import lombok.*;

import javax.validation.constraints.Email;
import java.time.LocalDate;

@Getter @Setter @Builder
@NoArgsConstructor @AllArgsConstructor
public class CreateUserDto {

    @NonNull
    private String avatar;
    private LocalDate birthday;
    @UniqueName(message = "{POI.nombre.unique}")
    private String fullName;
    @Email
    private String email;
    @NonNull
    private boolean privacy;
    private String password;
    private String password2;
}