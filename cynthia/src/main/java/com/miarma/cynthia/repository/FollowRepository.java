package com.miarma.cynthia.repository;

import com.miarma.cynthia.models.Follow;
import com.miarma.cynthia.models.FollowPK;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FollowRepository extends JpaRepository<Follow, FollowPK> {

}
