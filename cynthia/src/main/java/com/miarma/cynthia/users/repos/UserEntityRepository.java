package com.miarma.cynthia.users.repos;

import com.miarma.cynthia.models.Follow;
import com.miarma.cynthia.users.model.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findFirstByEmail(String email);
    Optional<UserEntity> findFirstByfullName(String fullName);

    @EntityGraph("graph_followers")
    List<Follow> findAllFollowersById(UUID id);
}
