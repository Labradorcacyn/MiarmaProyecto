package com.miarma.cynthia.users.repos;

import com.miarma.cynthia.models.Follow;
import com.miarma.cynthia.users.model.UserEntity;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserEntityRepository extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findFirstByEmail(String email);

    Optional<UserEntity> findByFullName(String fullName);

    @Query("""
            select u.followers from UserEntity u
            where u.id = :id
            """)
    List<Follow> findAllFollower(@Param("id")UUID id);
}
