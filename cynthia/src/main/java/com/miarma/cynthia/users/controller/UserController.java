package com.miarma.cynthia.users.controller;

import com.miarma.cynthia.security.dto.JwtUserResponse;
import com.miarma.cynthia.security.dto.LoginDto;
import com.miarma.cynthia.security.jwt.JwtProvider;
import com.miarma.cynthia.users.dto.users.CreateUserDto;
import com.miarma.cynthia.users.dto.users.GetUserDto;
import com.miarma.cynthia.users.dto.users.UserDtoConverter;
import com.miarma.cynthia.users.model.UserEntity;
import com.miarma.cynthia.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserEntityService userEntityService;
    private final UserDtoConverter userDtoConverter;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(
                                loginDto.getEmail(),
                                loginDto.getPassword()
                        )
                );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtProvider.generateToken(authentication);
        UserEntity user = (UserEntity) authentication.getPrincipal();
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(convertUserToJwtUserResponse(user, jwt));
    }

    @GetMapping("/me")
    public ResponseEntity<?>me(@AuthenticationPrincipal UserEntity user){
        return ResponseEntity.ok(convertUserToJwtUserResponse(user, null));
    }

    private JwtUserResponse convertUserToJwtUserResponse(UserEntity user, String jwt) {
        return JwtUserResponse.builder()
                .fullName(user.getFullName())
                .email(user.getEmail())
                .avatar(user.getAvatar())
                .role(user.getRole().name())
                .token(jwt)
                .build();
    }

    @PostMapping("/auth/register")
    public ResponseEntity<GetUserDto> newUser (@RequestPart("file") MultipartFile file,  @RequestPart("body") CreateUserDto createUserDto) /*throws IOException */{
        UserEntity saved = userEntityService.registrarUsuario(createUserDto, file);

        if (saved == null)
            return ResponseEntity.badRequest().build();
        else
            return ResponseEntity.ok(userDtoConverter.convertUserEntityToGetUserDto(saved));
    }
}