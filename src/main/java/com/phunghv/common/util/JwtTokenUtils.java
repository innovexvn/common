package com.phunghv.common.util;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.lang3.StringUtils;

public class JwtTokenUtils {
    private JwtTokenUtils() {
    }

    public static String extractToken(HttpServletRequest request) {
        final String authHeader = request.getHeader("Authorization");
        if (StringUtils.isEmpty(authHeader) || !StringUtils.startsWith(authHeader, "Bearer ")) {
            return null;
        }
        return authHeader.substring(7);
    }
}
