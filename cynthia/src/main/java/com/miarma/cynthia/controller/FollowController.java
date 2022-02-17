package com.miarma.cynthia.controller;

import com.miarma.cynthia.models.Follow;
import com.miarma.cynthia.service.FollowService;
import com.miarma.cynthia.users.dto.follow.FollowDtoConverter;
import com.miarma.cynthia.users.dto.follow.GetFollowDto;
import com.miarma.cynthia.users.model.UserEntity;
import com.miarma.cynthia.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final UserEntityService userEntityService;
    private final FollowService followService;
    private final FollowDtoConverter followDtoConverter;

    @PostMapping("/follow/{nick}")
    public ResponseEntity<GetFollowDto> followUser(@PathVariable String nick, @AuthenticationPrincipal UserEntity currentUser){
        UserEntity user = userEntityService.findByFullName(nick);
        Follow follow = new Follow();
        follow.addSeguidorSeguido(currentUser, user);
        followService.save(follow);
        return ResponseEntity.ok().body(followDtoConverter.convertFollowToGetFollowtDto(follow));
    }

    @PostMapping("/follow/accept/{id}")
    public ResponseEntity<List<UserEntity>> acceptFollow(@PathVariable UUID id, @AuthenticationPrincipal UserEntity currentUser){
        Optional<UserEntity> user = userEntityService.findById(id);
        return null;
    }

    @PostMapping("/follow/decline/{id}")
    public ResponseEntity<List<UserEntity>> declineFollow(@PathVariable UUID id, @AuthenticationPrincipal UserEntity currentUser){
        return null;
    }

    @GetMapping("/follow/list")
    public ResponseEntity <List<Follow>> followList(@AuthenticationPrincipal UserEntity user){
        return ResponseEntity.ok().body(userEntityService.GetMyFollowers(user.getId()));
    }
}