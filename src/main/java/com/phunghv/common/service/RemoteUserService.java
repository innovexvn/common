package com.phunghv.common.service;

import com.phunghv.common.dto.UserInfo;

public interface RemoteUserService {
    UserInfo loadUserByUsername(String userEmail);

    UserInfo loadDetailInfo(UserInfo userInfo);
}
