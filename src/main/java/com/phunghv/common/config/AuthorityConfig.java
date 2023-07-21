package com.phunghv.common.config;

import com.phunghv.common.util.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service("el")
@Slf4j
public class AuthorityConfig {
    /**
     * Use = add before method  @PreAuthorize("@el.check('storage:list')")
     *
     * @param permissions
     * @return
     */
    public boolean check(String... permissions) {
        var elPermissions = SecurityUtils.getCurrentUser().getAuthorities().stream().map(GrantedAuthority::getAuthority).toList();
        return elPermissions.contains("super-admin") || Arrays.stream(permissions).anyMatch(elPermissions::contains);
    }
}
