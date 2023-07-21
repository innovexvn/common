package com.phunghv.common.service;

import com.phunghv.common.dto.UserInfo;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtService {
    String extractUserName(String token);

    UserInfo extractUserInfo(String token);

    String generateToken(UserInfo userDetails);

    boolean isTokenValid(String token, UserDetails userDetails);
}
