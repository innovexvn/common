package com.phunghv.common.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service("el")
@Slf4j
public class AuthorityConfig {
    /**
     * Use = add before method  @PreAuthorize("@el.check('storage:list')")
     *
     * @param permissions
     * @return
     */
    public Boolean check(String... permissions) {
        log.info("Check permission : {}", permissions);
        return true;
    }
}
