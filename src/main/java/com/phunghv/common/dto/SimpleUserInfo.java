package com.phunghv.common.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.Collection;

@Getter
@Builder
public class SimpleUserInfo implements UserInfo {
    private String username;
    private String email;
    private Collection<String> roleSet;
    private boolean active;
}
