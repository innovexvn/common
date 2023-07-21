package com.phunghv.common.service.impl;

import com.phunghv.common.dto.SimpleUserInfo;
import com.phunghv.common.dto.UserInfo;
import com.phunghv.common.service.JwtService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {
    private static final long EXPIRED_TIME = 1000 * 60 * 24 * 7;
    @Value("${token.signing.key}")
    private String jwtSigningKey;

    @Override
    public String extractUserName(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    @Override
    public UserInfo extractUserInfo(String token) {
        var claims = extractAllClaims(token);
        return SimpleUserInfo.builder()
                .username(claims.getSubject())
                .email(claims.get("email", String.class))
                .roleSet(claims.get("roles", Collection.class))
                .active(true)
                .build();
    }


    @Override
    public String generateToken(UserInfo userDetails) {
        return generateToken(new HashMap<>(), userDetails);
    }

    @Override
    public boolean isTokenValid(String token, UserDetails userDetails) {
        final String userName = extractUserName(token);
        return (userName.equals(userDetails.getUsername())) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimsResolvers) {
        final Claims claims = extractAllClaims(token);
        return claimsResolvers.apply(claims);
    }

    private String generateToken(Map<String, Object> extraClaims, UserInfo userInfo) {
        var currentTime = System.currentTimeMillis();
        return Jwts.builder().setClaims(extraClaims)
                .setSubject(userInfo.getUsername())
                .claim("email", userInfo.getEmail())
                .claim("roles", userInfo.getRoleSet())
                .setIssuedAt(new Date(currentTime))
                .setExpiration(new Date(currentTime + EXPIRED_TIME))
                .signWith(getSigningKey(), SignatureAlgorithm.HS256).compact();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parserBuilder().setSigningKey(getSigningKey()).build().parseClaimsJws(token)
                .getBody();
    }

    private Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtSigningKey);
        return Keys.hmacShaKeyFor(keyBytes);
    }
}
