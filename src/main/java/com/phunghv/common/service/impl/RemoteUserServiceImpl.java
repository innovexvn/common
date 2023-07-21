package com.phunghv.common.service.impl;

import com.phunghv.common.dto.SimpleUserInfo;
import com.phunghv.common.dto.UserInfo;
import com.phunghv.common.service.RemoteUserService;
import org.springframework.stereotype.Service;

@Service
public class RemoteUserServiceImpl implements RemoteUserService {
    @Override
    public UserInfo loadUserByUsername(String userEmail) {
        return SimpleUserInfo.builder()
                .username(userEmail)
                .build();
    }

    @Override
    public UserInfo loadDetailInfo(UserInfo userInfo) {
        return userInfo;
    }
}
