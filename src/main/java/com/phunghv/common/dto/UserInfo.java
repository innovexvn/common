package com.phunghv.common.dto;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public interface UserInfo extends UserDetails {
    String getUsername();

    String getEmail();

    Collection<String> getRoleSet();

    boolean isActive();

    @Override
    default Collection<? extends GrantedAuthority> getAuthorities() {
        if (CollectionUtils.isEmpty(getRoleSet())) {
            return Collections.emptyList();
        }
        return getRoleSet().stream().map(SimpleGrantedAuthority::new).toList();
    }

    @Override
    default String getPassword() {
        return null;
    }

    @Override
    default boolean isAccountNonExpired() {
        return true;
    }

    @Override
    default boolean isAccountNonLocked() {
        return true;
    }

    @Override
    default boolean isCredentialsNonExpired() {
        return true;
    }

    default boolean isEnabled() {
        return isActive();
    }
}
