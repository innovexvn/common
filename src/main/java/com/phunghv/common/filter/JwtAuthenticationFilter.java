package com.phunghv.common.filter;

import com.phunghv.common.service.JwtService;
import com.phunghv.common.service.RemoteUserService;
import com.phunghv.common.util.JwtTokenUtils;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final RemoteUserService remoteUserService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        var jwt = JwtTokenUtils.extractToken(request);

        if (StringUtils.isEmpty(jwt)) {
            filterChain.doFilter(request, response);
            return;
        }

        var userInfo = jwtService.extractUserInfo(jwt);
        if (userInfo != null && StringUtils.isNotEmpty(userInfo.getUsername()) && SecurityContextHolder.getContext().getAuthentication() == null) {
            var userDetails = remoteUserService.loadDetailInfo(userInfo);
            if (jwtService.isTokenValid(jwt, userDetails)) {
                var context = SecurityContextHolder.createEmptyContext();
                var authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                context.setAuthentication(authToken);
                SecurityContextHolder.setContext(context);
            }
        }
        filterChain.doFilter(request, response);
    }
}
