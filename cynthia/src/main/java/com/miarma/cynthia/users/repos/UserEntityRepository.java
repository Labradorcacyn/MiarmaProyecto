package com.miarma.cynthia.users.repos;

import com.miarma.cynthia.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findFirstByEmail(String email);
    Optional<UserEntity> findFirstByfullName(String fullName);
}
