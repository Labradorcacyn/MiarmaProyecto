package com.miarma.cynthia.repository;

import com.miarma.cynthia.models.Follow;
import com.miarma.cynthia.models.FollowPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface FollowRepository extends JpaRepository<Follow, FollowPK> {

    @Query("""
            select f.seguidor from Follow f
            where f.seguido.id = :id
            and f.isFollow = :boolean
            """)
    List<Follow> findFollowers(@Param("id")UUID id, @Param("boolean") boolean isFollow);
}
