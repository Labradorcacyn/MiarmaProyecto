package com.miarma.cynthia.controller;

import com.miarma.cynthia.models.Follow;
import com.miarma.cynthia.service.FollowService;
import com.miarma.cynthia.users.dto.follow.FollowDtoConverter;
import com.miarma.cynthia.users.dto.follow.GetFollowDto;
import com.miarma.cynthia.users.dto.users.GetUserDto;
import com.miarma.cynthia.users.model.UserEntity;
import com.miarma.cynthia.users.services.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/follow")
@RequiredArgsConstructor
public class FollowController {

    private final UserEntityService userEntityService;
    private final FollowService followService;
    private final FollowDtoConverter followDtoConverter;

    @PostMapping("/follow/{nick}")
    public ResponseEntity<GetFollowDto> followUser(@PathVariable String nick, @AuthenticationPrincipal UserEntity currentUser){
        UserEntity user = userEntityService.findFirstByFullName(nick);
        Follow follow = new Follow();
        follow.addSeguidorSeguido(currentUser, user);
        followService.save(follow);
        return ResponseEntity.ok().body(followDtoConverter.convertFollowToGetFollowtDto(follow));
    }

    @PostMapping("/follow/accept/{id}")
    public ResponseEntity<List<GetFollowDto>> acceptFollow(@PathVariable UUID id, @AuthenticationPrincipal UserEntity currentUser){
        List<Follow> request = followService.findMyFollowers(currentUser.getId(), false);

        if(request.contains(userEntityService.findById(id))){
            request.stream().map(f -> f.isFollow() == true);
            return ResponseEntity.ok().body(request.stream().map(f-> followDtoConverter.convertFollowToGetFollowtDto(f)).collect(Collectors.toList()));
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/follow/decline/{id}")
    public ResponseEntity<List<GetFollowDto>> declineFollow(@PathVariable UUID id, @AuthenticationPrincipal UserEntity currentUser){
        List<Follow> request = followService.findMyFollowers(currentUser.getId(), false);
        if(request.contains(userEntityService.findById(id))){
            request.remove(userEntityService.findById(id));
            return ResponseEntity.ok().body(request.stream().map(f-> followDtoConverter.convertFollowToGetFollowtDto(f)).collect(Collectors.toList()));
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/follow/list")
    public ResponseEntity <List<Follow>> followListRequest(@AuthenticationPrincipal UserEntity user){
        return ResponseEntity.ok().body(followService.findMyFollowers(user.getId(), false));
    }
}