package com.miarma.cynthia.users.repos;

import com.miarma.cynthia.users.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findFirstByEmail(String email);

    Optional<UserEntity> findFirstByFullName(String fullName);

    boolean existsByFullName(String fullName);
/*
    @Query("""
            select u.followers from UserEntity u
            where u.id = :id
            """)
    List<Follow> findAllFollower(@Param("id")UUID id);*/
}
