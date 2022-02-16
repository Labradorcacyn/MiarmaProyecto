package com.miarma.cynthia.service;

import com.miarma.cynthia.models.Follow;
import com.miarma.cynthia.models.FollowPK;
import com.miarma.cynthia.repository.FollowRepository;
import com.miarma.cynthia.service.base.BaseService;
import org.springframework.stereotype.Service;

@Service
public class FollowService extends BaseService <Follow, FollowPK, FollowRepository> {
}
