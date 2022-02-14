package com.miarma.cynthia.users.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetUserDto {

    private String avatar;
    private String birthday;
    private String fullName;
    private String email;
    private Boolean isPrivate;
    private String role;



}
