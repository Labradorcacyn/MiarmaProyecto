package com.miarma.cynthia;

import com.miarma.cynthia.users.dto.CreateUserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@RequiredArgsConstructor
public class prueba {

    @PostConstruct
    public void prueba() {
        CreateUserDto admin = CreateUserDto.builder()
                .fullname("admin")
                .email("admin@gmail.com")
                .password("admin1234")
                .password2("admin1234")
                .avatar("avatar1.jpg")
                .build();

    }
}